<template>
  <v-container fluid>
    <v-row>
      <v-col cols="6">
        <div class="left-panel">
          <!-- 환자 정보 입력 폼 -->
          <PatientForm @patient-submitted="handlePatientSubmitted" />

          <!-- 선택한 병원 정보 표시 -->
          <div v-if="selectedHospital" class="hospital-info">
            <h3>선택된 병원 정보</h3>
            <p>병원 이름: {{ selectedHospital.hospitalName }}</p>
            <p>도로명주소: {{ selectedHospital.roadAddress }}</p>
            <p>지번주소: {{ selectedHospital.jibunAddress }}</p>
            <p>남은 병상: {{ selectedHospital.remain }}</p>
            <p>Beds ID: {{ selectedHospital.bedsId }}</p>

            <v-btn color="success" @click="reserveBed">예약</v-btn>
          </div>

          <!-- 기존 예약 내역 표시 -->
          <ReservationsList
            :reservations="reservations"
            :checkedReservations="checkedReservations"
            @update-checked-reservations="handleUpdateCheckedReservations"
          />
        </div>
      </v-col>

      <v-col cols="6">
        <div class="right-panel">
          <KakaoMap
            :latitude="latitude"
            :longitude="longitude"
            @select-hospital="handleSelectHospital"
          />
        </div>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import KakaoMap from './KakaoMap.vue';
import PatientForm from './PatientForm.vue';
import ReservationsList from './ReservationsList.vue';
import { useRouter } from 'vue-router';

export default {
  components: {
    KakaoMap,
    PatientForm,
    ReservationsList
  },
  data() {
    return {
      latitude: null,
      longitude: null,
      patientId: null,
      selectedHospital: null,
      reservations: [],
      checkedReservations: {},
      checked: false,
      fetchInterval: null,
      router: useRouter()
    };
  },
  created() {
    /*
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(this.setPosition, this.handleError);
    } else {
      alert('Geolocation is not supported by this browser.');
    }
    */
    this.setPosition([37.358827,127.1148689])
    this.fetchReservations();
    this.startFetchingReservations();
  },
  beforeDestroy() {
    if (this.fetchInterval) {
      clearInterval(this.fetchInterval);
    }
  },
  methods: {
    /*
    setPosition(position) {
      this.latitude = position.coords.latitude;
      this.longitude = position.coords.longitude;
    },
    */
    setPosition(position) {
      this.latitude = position[0];
      this.longitude = position[1];
    },
    handleError(error) {
      console.error('Error getting geolocation:', error);
      alert('Unable to retrieve your location.');
    },
    handleSelectHospital(hospitalData) {
      this.selectedHospital = hospitalData;
    },
    handlePatientSubmitted(patientHref) {
      this.patientId = this.extractPatientIdFromUrl(patientHref);
    },
    extractPatientIdFromUrl(url) {
      const urlParts = url.split('/');
      return urlParts[urlParts.length - 1];
    },
    reserveBed() {
      if (!this.selectedHospital || !(this.patientId || this.checkedReservations)) {
        alert('환자 정보와 병원 정보를 모두 입력해주세요.');
        return;
      }
      
      const payload = {
        bedsId: this.selectedHospital.bedsId,
        reservationId: null,
        patientId: null,
        status: null
      }

      if(this.checked) {
        payload.patientId = this.checkedReservations.patientId
        payload.status = "요청";
        payload.reservationId = this.checkedReservations.hospitalizationId
        fetch(`http://20.249.182.66:8080/hospitalizationInfos/${this.checkedReservations.hospitalizationId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(payload)
        }).then(response => response.json())
        .then(data => {
            alert(`${this.checkedReservations.hospitalizationId}번 예약이 업데이트 되었습니다`)
            this.checkedReservations = {}
            window.location.reload()
        })
        .catch(error => {
            alert(`${this.checkedReservations.hospitalizationId}번 예약 업데이트 중 오류가 발생했습니다`)
        })
      }  else {
        payload.patientId = this.patientId
        alert('patient insert')
        fetch(`http://20.249.182.66:8080/reservations`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(payload)
        }).then(response => response.json())
        .then(data => {
            alert(`예약이 완료되었습니다`)
        })
        .catch(error => {
            alert(`예약중 오류가 발생했습니다`)
        })
      }



    //   if (selectedReservations.length === 0) {
    //     alert('승인할 예약을 선택해주세요.');
    //     return;
    //   }

    //   selectedReservations.forEach(bedsId => {
    //     const payload = {
    //       patientId: this.patientId,
    //       bedsId: this.selectedHospital.bedsId,
    //       hospitalId: 'some_hospital_id',
    //       admissionDate: new Date().toISOString(),
    //       dischargeDate: null
    //     };

    //     fetch(`http://20.249.182.66:8080/hospitalizations/${bedsId}`, {
    //       method: 'PUT',
    //       headers: {
    //         'Content-Type': 'application/json'
    //       },
    //       body: JSON.stringify(payload)
    //     })
    //       .then(response => response.json())
    //       .then(data => {
    //         alert(`예약 ${bedsId}이(가) 성공적으로 업데이트되었습니다.`);
    //         this.fetchReservations();
    //       })
    //       .catch(error => {
    //         console.error('Error updating reservation:', error);
    //         alert('예약을 업데이트하는 중 오류가 발생했습니다.');
    //       });
    //   });
    },
    fetchReservations() {
      fetch('http://20.249.182.66:8080/hospitalizationInfos')
        .then(response => response.json())
        .then(data => {
          const reservations = data._embedded.hospitalizationInfos;

          const fetchDetails = reservations.map((reservation, index) => {
            return Promise.all([
              fetch(`http://20.249.182.66:8080/patients/${reservation.patientId}`).then(res => res.json()),
              fetch(`http://20.249.182.66:8080/hospitals/${reservation.bedsId}`).then(res => res.json())
            ]).then(([patientData, bedData]) => {
              return {
                patientName: patientData.patientName,
                patientDisease: patientData.patientDisease,
                patientNumber: patientData.patientNumber,
                hospitalName: bedData.hospitalName,
                hospitalizationId: reservation.reservationId,
                patientId: reservation.patientId,
                status: reservation.status,
                checked: false
              };
            });
          });

          Promise.all(fetchDetails).then(results => {
            this.reservations = results;
          });
        })
        .catch(error => {
          console.error('Error fetching reservations:', error);
        });
    },
    startFetchingReservations() {
      this.fetchInterval = setInterval(() => {
        this.fetchReservations();
      }, 2000);
    },
    handleUpdateCheckedReservations({ reservation, checked }) {
        alert(JSON.stringify(reservation))
        this.checked = !this.checked
        this.checkedReservations = reservation
    //   this.$set(this.checkedReservations, reservation, checked);
    }
  }
};
</script>

<style>
.left-panel {
  background-color: #f5f5f5;
  height: 100vh;
  padding: 16px;
}

.right-panel {
  background-color: #e0e0e0;
  height: 100vh;
  padding: 0;
}

.hospital-info {
  margin-top: 20px;
}

.reservations {
  margin-top: 20px;
}
</style>
