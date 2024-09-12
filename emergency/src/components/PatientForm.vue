<template>
  <v-form v-model="valid" @submit.prevent="submitPatientForm">
    <v-text-field
      v-model="patient.name"
      label="환자 이름"
      :rules="[rules.required]"
      required
    ></v-text-field>

    <v-text-field
      v-model="patient.disease"
      label="질병 명"
      :rules="[rules.required]"
      required
    ></v-text-field>

    <v-text-field
      v-model="patient.phone"
      label="전화번호"
      :rules="[rules.required, rules.phone]"
      required
    ></v-text-field>

    <v-btn :disabled="!valid" color="primary" type="submit">
      입력
    </v-btn>
  </v-form>
</template>

<script>
export default {
  props: {
    patientId: Number
  },
  data() {
    return {
      valid: false,
      patient: {
        name: '',
        disease: '',
        phone: ''
      },
      rules: {
        required: value => !!value || '필수 입력 항목입니다.',
        phone: value => {
          const phonePattern = /^[0-9]{3}-[0-9]{4}-[0-9]{4}$/;
          return phonePattern.test(value) || '유효한 전화번호를 입력하세요 (예: 010-1234-5678)';
        }
      }
    };
  },
  methods: {
    submitPatientForm() {
      const payload = {
        patientName: this.patient.name,
        patientDisease: this.patient.disease,
        patientNumber: this.patient.phone
      };

      fetch('http://20.249.182.66:8080/patients', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(payload)
      })
        .then(response => response.json())
        .then(data => {
          this.$emit('patient-submitted', data._links.patient.href);
          alert('환자 정보가 성공적으로 제출되었습니다.');
        })
        .catch(error => {
          console.error('Error submitting patient data:', error);
          alert('환자 정보를 제출하는 중 오류가 발생했습니다.');
        });
    }
  }
};
</script>
