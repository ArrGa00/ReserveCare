<template>
  <div v-if="selectedHospital" class="hospital-info">
    <h3>선택된 병원 정보</h3>
    <p>병원 이름: {{ selectedHospital.hospitalName }}</p>
    <p>도로명주소: {{ selectedHospital.roadAddress }}</p>
    <p>지번주소: {{ selectedHospital.jibunAddress }}</p>
    <p>남은 병상: {{ selectedHospital.remain }}</p>
    <p>Beds ID: {{ selectedHospital.bedsId }}</p>

    <v-btn color="success" @click="reserveBed">예약</v-btn>
  </div>
</template>

<script>
export default {
  props: {
    selectedHospital: Object
  },
  methods: {
    reserveBed() {
      this.$emit('reserve');
    }
  }
};
</script>
