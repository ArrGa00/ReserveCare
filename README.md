# ⭐ 응급구조 양방향 서비스 ⭐

---

**응급구조 양방향 서비스**는 병상 가용성, 환자 입원 및 예약 추적을 관리하는 종합 솔루션이다. </br> 
마이크로서비스 아키텍처를 기반으로 구축되어 확장성, 모듈성 및 통합 용이성을 제공한다.

---

## ☑ 개요

입원 관리 시스템은 구급대원과 병원의 환자 이송 연결 및 운영을 효율적으로 관리하기 위해 설계하였다. </br>
병상 가용성 정보를 실시간으로 수집하고, 수집된 데이터를 토대로 환자 입원을 신청/취소하여 병상 예약 요청을 관리한다. </br>
병원, 환자 및 예약 데이터를 통합하여 병상 자원 활용과 환자 연계 흐름을 개선하는 데 기여할 수 있다.

[**프로젝트 설계도**] </br>
![img](https://github.com/user-attachments/assets/7048f84a-b845-4d17-9a09-ff7a83692a5c)

---
## ☑ 서비스 시나리오
### ▶ 기능적 요구사항
1. 구급 대원은 환자 정보를 등록/수정/삭제할 수 있다.
2. 현재 위치(GPS) 혹은 특정 지역 내에서 조건(거리, 가용 병상 유무 등) 을 만족하는 병원을 MAP 형태로 볼 수 있다.
3. 예약이 이루어진다면 예약 내역(Message)이 전달된다.
4. 예약 사항이 취소될 경우 취소 내역이 전달된다.
5. 병원관리자는 예약 신청을 접수하거나 반려할 수 있다.
6. 환자 이송이 완료되면 이송 건을 종료하며 가용 병상 정보를 업데이트한다.
7. 환자 퇴원시 가용병상 정보를 업데이트한다.

###  ▶ 비기능적 요구사항
(작성중)

---

## ☑ 마이크로서비스 아키텍처

### 1. 병원 서비스
병원 서비스는 병원의 자원, 특히 가용 병상을 실시간으로 관리한다.</br>
초기 데이터는 공공데이터 포털에서 제공 받아 초기화한다.</br>
입원 정보 서비스와 연계하여 최신 병상 가용 정보를 제공한다.</br>

- **주요 기능:**
  - 병원 자원 (병상) 관리
  - 실시간 의료 자원 가용성 모니터링

### 2. 환자 서비스
병원에 입원하기 위한 환자 관련 모든 데이터를 처리한다.</br>
예약 및 입퇴원 정보 서비스와 연결된다.

- **주요 기능:**
  - 환자 등록 및 데이터 관리

### 3. 예약 서비스
예약 서비스는 병상 예약 요청을 관리하고 추적한다. </br>
가용 가능한 병상 배정에 입원 요청을 전송/취소 할 수 있다.</br>
따라서 구급 대원은 환자 입원 예약 과정을 원활하게 처리할 수 있다.</br>

- **주요 기능:**
  - 병상 예약 관리
  - 실시간 가용성에 기반한 병상 예약 요청

### 4. 입원 정보 서비스
환자의 입퇴원 정보를 관리한다. </br>
병원은 구급대원이 요청한 환자들의 입원 요청을 승인/거절할 수 있다</br>

- **주요 기능:**
    - 환자의 입/퇴원 정보
    - 병상 점유 상태 업데이트
    - 환자 입원 예약을 승인/취소
---

## ☑ 차별점

- **실시간 병상 가용성**: 시스템은 병상의 실시간 가용성을 추적하고, 병원 직원이 신속하게 정보를 기반으로 결정을 내릴 수 있도록 한다.

- **환자 중심 설계**: 환자 정보를 제공하여 병원이 필요한 환자 정보를 손쉽게 확인할 수 있도록 한다.

- **예약 관리**: 병상 가용성에 따라 예약 요청을 승인 또는 거부할 수 있으며, 이를 통해 환자 대기 시간을 줄이고 입원 절차를 최적화할 수 있다.

- **마이크로서비스 확장성**: 시스템의 각 서비스는 독립적으로 확장 가능하다.

---

## ☑ 설계

### Event Storming 결과

#### - 이벤트 도출 초안
![image2](https://github.com/user-attachments/assets/8260ffed-d39f-49a0-92e1-2b58d91bcc36)

#### - 최종 Event 도출 결과
![image3](https://github.com/user-attachments/assets/e2a148e8-2fa1-45ff-9afe-a3afc2118f41)
- 이벤트스토밍 과정 중 도출된 잘못된 도메인 이벤트들을 걸러내는 작업을 수행함
- 하단 세 가지의 이벤트는 event가 아닌 policy의 개념이거나, 불필요한 행동이므로 이벤트에서 삭제

### MSAEZ 유즈케이스
1. Actor와 Command를 부착하여 가독성 높이기
   ![image4](https://github.com/user-attachments/assets/97ba2e8c-2c05-4161-9fe2-290e327146f1)

2. Aggregate으로 묶기
   ![image5](https://github.com/user-attachments/assets/e9af4816-09b1-414f-be83-5198fa12abd2)

3. 바운디드 컨텍스트로 묶기
   ![image6](https://github.com/user-attachments/assets/bcf2e4cd-e51d-4bce-a554-45fd8eead68d)

4. 폴리시 부착
   ![image7](https://github.com/user-attachments/assets/8bff5404-bd64-453a-bbb1-9bd771f86ba6)

5. Policy의 이동과 Context 매핑
   ![image8](https://github.com/user-attachments/assets/6dd9d0c5-64a4-4436-9b0f-f3ea69ccc518)

6. 기능적 요구사항 추적
   ![image9](https://github.com/user-attachments/assets/098687d1-fdfb-4079-b788-86e766129f41)
6-1. 구급 대원은 환자 정보를 등록/수정/삭제할 수 있다. (완료)
   ![image10](https://github.com/user-attachments/assets/91ce888c-db71-4a0e-a749-24e58d66e873)
6-2. 현재 위치(GPS) 혹은 특정 지역 내에서 조건(거리, 가용 병상 유무 등) 을 만족하는 병원을 MAP 형태로 볼 수 있다. (완료)
   ![image11](https://github.com/user-attachments/assets/a214fdaf-cfda-4b54-a075-335dc5bba200)
6-3. 예약이 이루어진다면 예약 내역(Message)이 전달된다. (완료) </br>
6-4. 예약 사항이 취소될 경우 취소 내역이 전달된다. (완료) </br>
6-5. 병원관리자는 예약 신청을 접수하거나 반려할 수 있다. (완료)
   ![image12](https://github.com/user-attachments/assets/44606ec2-e859-4af5-a8c3-e0c7614557ae)
6-6. 환자 이송이 완료되면 이송 건을 종료하며 가용 병상 정보를 업데이트한다. (완료)
   ![image13](https://github.com/user-attachments/assets/8a6c1fd2-1f74-42fa-bdb8-c4ba39284b04)
6-7. 환자 퇴원시 가용병상 정보를 업데이트한다. (완료)
---
## ☑ 구현
분석/설계 단계에서 도출된 아키텍처에 따라, 각 마이크로 서비스들을 스프링부트로 구현하였다. 구현한 각 서비스를 로컬에서 실행하는 방법은 아래와 같다.
```bash
cd gateway/
mvn spring-boot:run

cd hospital/
mvn spring-boot:run

cd patient/
mvn spring-boot:run

cd reservation/
mvn spring-boot:run

cd hospitalizationInfo/
mvn spring-boot:run
```

### · DDD 의 적용

각 서비스내에 도출된 핵심 Aggregate Root 객체를 Entity 로 선언하였다. 이때 가능한 현업에서 사용하는 언어 (유비쿼터스 랭귀지)를 그대로 사용하였다.
```java
@Entity
@Table(name="Hospital_table")
@Data
public class Hospital  {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String hospitalName;
    private Double lat;
    private Double lng;
    private Integer remain;
    private Integer totalBeds;
    private String hpid;
}
```
Entity Pattern 과 Repository Pattern 을 적용하여 JPA 를 통하여 다양한 데이터소스 유형 (RDB or NoSQL) 에 대한 별도의 처리가 없도록 데이터 접근 어댑터를 자동 생성하기 위하여 Spring Data REST 의 RestRepository 를 적용하였다.
```java
@RepositoryRestResource(collectionResourceRel = "hospitals", path = "hospitals")
public interface HospitalRepository
    extends PagingAndSortingRepository<Hospital, Long> {
    @Query(
        value = "select hospital " +
        "from Hospital hospital " +
        "where(:hpid is null or hospital.hpid like %:hpid%)"
    )
    Hospital findByGetBedsIdByhpid(String hpid);
}
```
적용 후 REST API 의 테스트
```bash
# 병원 데이터 확인
http :8088/hospitals

# 공공데이터 포털 API를 사용하여 전체/가용 병상 정보를 DB에 업데이트 (초기화)
http PUT :8088/hospitals/initializebeds

# 환자 등록
http POST :8088/patients patientName=LeeGa00 patientDisease=과다출혈 patientNumber=1 status=상태

# 환자 수정 및 삭제
http PUT :8088/patients/1/patientupdate patientName=LeeGa00 patientDisease=과다출혈 patientNumber=1 status=상태변경
http PUT :8088/patients/1/patientdelete

# 병원 예약 생성
http POST :8088/reservations patientId=1 bedsId=1

# 예약 요청 전체 확인
http :8088/reservations

# 병원이 요청받은 내역 전체 확인
http :8088/hospitalizationInfos

# 병원의 승인을 받기 이전에 병상 예약 요청을 취소
http PUT :8088/reservations/1/hospitalizationcancel

# 병원에서 요청을 승인
http PUT :8088/hospitalizationInfos/1/approve

# 병원에서 요청을 거부
http PUT :8088/hospitalizationInfos/1/reject

# 입원한 환자에게 퇴원 요청
http PUT :8088/hospitalizationInfos/1/discharge
```

### · 동기식 호출 과 비동기식
병원이 환자의 입원 요청을 승인한 경우, 환자의 정보를 입/퇴원 서비스에서 처리하기 위해 동기식 트랜잭션으로 처리하기로 한다.
앞서 Rest Repository 에 의해 노출되어있는 REST 서비스를 FeignClient 를 이용하여 호출하도록 한다.

- 환자 서비스를 호출하기 위해, Stub과 (FeignClient) 를 이용하여 Service 대행 인터페이스 (Proxy) 를 구현한다.
```java
@FeignClient(name = "patient", url = "${api.url.patient}")
public interface PatientService {
    @RequestMapping(method= RequestMethod.GET, path="patients/getNamebyId/{id}")
    public String getPatientNameById(@PathVariable("id") Long id);
}
```
- 예약 승인 이벤트가 발생한 이후 환자 정보를 요청하도록 처리한다.
```java
@Service
@Transactional
public class PolicyHandler {
    @StreamListener(
            value = KafkaProcessor.INPUT,
            condition = "headers['type']=='HospitalizationApproved'"
    )
    public void wheneverHospitalizationApproved_SearchPatientInfo (
            @Payload HospitalizationApproved hospitalizationApproved
    ) {
        try {
            HospitalizationApproved event = hospitalizationApproved;
            System.out.println(
                    "\n\n##### listener Test : " + hospitalizationApproved + "\n\n"
            );

            Optional<HospitalizationInfo> optionalHospitalizationInfo = hospitalizationInfoRepository.findById(
                    hospitalizationApproved.getId()
            );
            HospitalizationInfo hospitalizationInfo = optionalHospitalizationInfo
                    .orElseThrow(() -> new Exception("No Entity Found"));

            // REST Request
            String name = patientService.getPatientNameById(hospitalizationApproved.getPatientId());

            hospitalizationInfo.setPatientName(name);
            hospitalizationInfo.setStatus("승인");
            hospitalizationInfo.setStartDate(LocalDateTime.now());
            hospitalizationInfoRepository.save(hospitalizationInfo);

        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
        }
    }
}
```
- 결과
  ![승인으로이름가져오기](https://github.com/user-attachments/assets/4655d572-5c00-4f49-a213-fb5c8c06fbb6)

구급대원의 병원 예약을 취소를 병원에 알려주는 행위는 비동기식으로 처리한다.
병원 시스템의 처리를 위하여 병원 예약 요청이 블로킹 되지 않아도록 처리한다.

- 예약 요청 취소 되었다는 도메인 이벤트를 카프카로 송출한다. (Publish)
```java
public class HospitalizationInfo {
    public void reject() {
        HospitalizationRejected hospitalizationRejected = new HospitalizationRejected(this);
        hospitalizationRejected.publishAfterCommit();
    }
}
```
- 리스너가 이벤트를 받아서 입/퇴원 정보 관리 시스템에도 반영한다.
```java
public class PolicyHandler {
    @StreamListener(
            value = KafkaProcessor.INPUT,
            condition = "headers['type']=='HospitalizationCancelled'"
    )
    public void wheneverHospitalizationCanceled_UpdateStatus(
            @Payload HospitalizationCancelled hospitalizationCancelled
    ) {
        HospitalizationCancelled event = hospitalizationCancelled;
        System.out.println(
                "\n\n##### listener UpdateStatus : " +
                        hospitalizationCancelled +
                        "\n\n"
        );

        HospitalizationInfo.updateStatus(event);
    }
}
```
```java
public class HospitalizationInfo {
    public static void updateStatus(
            HospitalizationCancelled hospitalizationCancelled
    ) {
        repository().findByReservationId(hospitalizationCancelled.getId()).ifPresent(hospitalInfo -> {
            if (!hospitalInfo.getStatus().equals("승인")) {
                hospitalInfo.setStatus("요청취소");
                repository().save(hospitalInfo);
            } else {
                // 이벤트 발행 없이 취소가 안됨을 출력으로 확인 함
                System.out.println(
                        "\n\n##### 예약취소 불가능함\n\n"
                );
            }
        });
    }
}
```
- 결과

    <img src="https://github.com/user-attachments/assets/8c386514-c756-4ea0-977e-076978dfbaf7" width="400" height="200">

    ![병원에취소반영](https://github.com/user-attachments/assets/d9105ee5-4f98-40c6-9cb8-a7fab762ead1)

---
## ☑ 운영
(작성중)

---