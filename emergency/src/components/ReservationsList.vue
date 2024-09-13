<template>
  <div v-if="reservations.length" class="reservations">
    <h3>예약 내역</h3>
    <v-list class="scrollable-list">
      <v-list-item-group v-for="(reservation, index) in reservations" :key="reservation.index">
        <v-list-item>
          <template v-slot:prepend="{ isActive }">
            <v-list-item-action start>
              <v-checkbox
                :disabled="isDisabled(index)"
                v-if="reservation.status === '거절'"
                :input-value="checkedReservations[reservation.index] || false"
                @change="updateCheckbox(reservation,index, $event)"
              ></v-checkbox>
            </v-list-item-action>
          </template>
          <v-list-item-content>
            <v-list-item-title class="large-text">
              {{ reservation.patientName }} - {{ reservation.hospitalName }}
            </v-list-item-title>
            <v-list-item-subtitle class="large-text">
              질병: {{ reservation.patientDisease }}
            </v-list-item-subtitle>
            <v-list-item-subtitle class="large-text">
              전화번호: {{ reservation.patientNumber }}
            </v-list-item-subtitle>
            <v-list-item-subtitle class="large-text">
              상태: {{ reservation.status }}
            </v-list-item-subtitle>

            <!-- '요청' 상태일 때 '요청 취소' 버튼 추가 -->
            <v-btn
              v-if="reservation.status === '요청'"
              color="red"
              @click="cancelRequest(reservation.hospitalizationId)"
            >
              요청 취소
            </v-btn>
          </v-list-item-content>
        </v-list-item>

        <!-- v-divider를 사용하여 각 항목 사이에 경계선 추가 -->
        <v-divider v-if="index < reservations.length - 1" class="custom-divider"></v-divider>
      </v-list-item-group>
    </v-list>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  props: {
    reservations: Array,
    checkedReservations: Object
  },
  data() {
    return {
      checked: null,
      selectedReservation: []
    }
  },
  methods: {
    isDisabled(index) {
      return this.selectedReservation.length === 1 && this.selectedReservation[0] !== index
    },
    updateCheckbox(reservation,index, checked) {
      if(!this.checked) {
        this.checked = true
        this.selectedReservation.push(index)
      } else {
        this.checked = false
        this.selectedReservation.pop()
        reservation = {}
      }
      this.$emit('update-checked-reservations', { reservation, checked });
    },
    // 요청 취소 메소드 추가
    async cancelRequest(hospitalizationId) {
      try {
        const response = await axios.put(`http://20.249.182.66:8080/reservations/${hospitalizationId}/hospitalizationcancel`);
        console.log('요청 취소 성공:', response.data);
        // 요청 취소 후 필요한 처리 (예: 상태 업데이트)
        this.$emit('request-cancelled', hospitalizationId);
      } catch (error) {
        console.error('요청 취소 실패:', error);
      }
    }
  }
};
</script>

<style scoped>
.reservations {
  margin-top: 20px;
}

.scrollable-list {
  max-height: 600px;
  overflow-y: auto;
  background-color: #fff;
}


.large-text {
  height: 30px;
  overflow: visible;
  font-size: 20px; 
  padding: 0 2
}

.custom-divider {
  border-color: #000; /* 경계선 색상 설정 */
  border-width: 2px; /* 경계선 두께 설정 */
}
</style>
