<template>
  <div>
    <div id="map" style="width: 100%; height: 500px;"></div>
  </div>
</template>

<script>
export default {
  name: 'KakaoMap',
  props: {
    latitude: {
      type: Number,
      required: true
    },
    longitude: {
      type: Number,
      required: true
    }
  },
  data() {
    return {
      map: null,
      geocoder: null
    };
  },
  mounted() {
    if (window.kakao && window.kakao.maps) {
      this.loadMap();
    } else {
      this.loadScript();
    }
  },
  methods: {
    loadScript() {
      const script = document.createElement('script');
      script.src = '//dapi.kakao.com/v2/maps/sdk.js?appkey=5356302ac8d1bb40318613b2be2aadc3&autoload=false&libraries=services';
      script.onload = () => window.kakao.maps.load(this.loadMap);
      document.head.appendChild(script);
    },
    loadMap() {
      
      const container = document.getElementById('map');
      const options = {
        center: new window.kakao.maps.LatLng(this.latitude, this.longitude),
        level: 3
      };
      this.map = new window.kakao.maps.Map(container, options);
      
      this.geocoder = new window.kakao.maps.services.Geocoder();
      this.fetchHospitalData();
    },
    fetchHospitalData() {
      fetch('http://20.249.182.66:8080/hospitals?size=100')
        .then(response => response.json())
        .then(data => {
          const hospitals = data._embedded.hospitals;

          hospitals.forEach(hospital => {
            const position = new window.kakao.maps.LatLng(hospital.lat, hospital.lng);
            const marker = new window.kakao.maps.Marker({
              position: position
            });

            marker.setMap(this.map);

            const infoWindow = new window.kakao.maps.InfoWindow({
              content: `<div style="padding:5px;">${hospital.hospitalName}<br>잔여병상 : ${hospital.remain}</div>`
            });

            marker.addListener('click', () => {
              // _links.beds의 URL에서 bedsId 추출
              this.geocoder.coord2Address(hospital.lng, hospital.lat, (result, status) => {
                if(status == window.kakao.maps.services.Status.OK) {
                  const roadAddress = result[0].road_address ? result[0].road_address.address_name : '주소 없음';
                  const jibunAddress = result[0].address.address_name

                  const bedsId = this.extractBedsIdFromUrl(hospital._links.hospital.href);
              // 부모 컴포넌트로 데이터 전달
              this.$emit('select-hospital', {
                hospitalName: hospital.hospitalName,
                lat: hospital.lat,
                lng: hospital.lng,
                remain: hospital.remain,
                bedsId: bedsId,
                roadAddress: roadAddress,
                jibunAddress: jibunAddress
              });
                }
              })
              // const bedsId = this.extractBedsIdFromUrl(hospital._links.beds.href);
              
              // // 부모 컴포넌트로 데이터 전달
              // this.$emit('select-hospital', {
              //   hospitalName: hospital.hospitalName,
              //   lat: hospital.lat,
              //   lng: hospital.lng,
              //   remain: hospital.remain,
              //   bedsId: bedsId
              // });
            });
            infoWindow.open(this.map, marker);
          });
        })
        .catch(error => console.error('Error fetching hospital data:', error));
    },
    extractBedsIdFromUrl(url) {
      const urlParts = url.split('/');
      return urlParts[urlParts.length - 1]; // URL의 마지막 숫자를 bedsId로 추출
    },
    searchAddrFromCoords(coords, callback) {
    // 좌표로 행정동 주소 정보를 요청합니다
      this.geocoder.coord2RegionCode(coords.getLng(), coords.getLat(), callback);         
    },
    searchDetailAddrFromCoords(coords, callback) {
      this.geocoder.coord2Address(coords.getLng(), coords.getLat(), callback);
    }, 

  }
};
</script>

<style scoped>
#map {
  width: 100%;
  height: 500px;
}
</style>
