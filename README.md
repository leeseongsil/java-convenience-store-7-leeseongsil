# 편의점

### 구현할 기능 목록

#### 재고 관리

- [x] 각 상품의 재고 수량을 고려하여 결제 가능 여부를 확인한다.
- [ ] 고객이 상품을 구매할 때마다, 결제된 수량만큼 해당 상품의 재고에서 차감하여 수량을 관리한다.
  - 재고 수량 초과 요청 시, `[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.` 에러 메시지 반환

#### 프로모션 할인

- [ ] 프로모션 적용이 가능한 상품에 대해 고객이 해당 수량보다 적게 가져온 경우, 필요한 수량을 추가로 가져오면 혜택을 받을 수 있음을 안내한다.
- [ ] 프로모션 재고가 부족하여 일부 수량을 프로모션 혜택 없이 결제해야 하는 경우, 일부 수량에 대해 정가로 결제하게 됨을 안내한다.
  - [ ] 프로모션 혜택은 프로모션 재고 내에서만 적용할 수 있다.
- [ ] 프로모션 기간 중이라면 프로모션 재고를 우선적으로 차감하며, 프로모션 재고가 부족할 경우에는 일반 재고를 사용한다.

- 조건
  - [ ] 오늘 날짜가 프로모션 기간 내에 포함된 경우에만 할인을 적용한다.
    - [ ] 시작 날짜가 종료 날짜보다 이전인 경우 에러를 발생시킨다
  - [ ] 프로모션은 N개 구매 시 1개 무료 증정(Buy N Get 1 Free)의 형태로 진행된다.
  - [ ] 1+1 또는 2+1 프로모션이 각각 지정된 상품에 적용된다.
  - [ ] 동일 상품에 여러 프로모션이 적용되지 않는다.

#### 멤버십 할인

- [ ] 멤버십 회원은 프로모션 미적용 금액의 30%를 할인받는다.
- [ ] 프로모션 적용 후 남은 금액에 대해 멤버십 할인을 적용한다.
- [ ] 멤버십 할인의 최대 한도는 8,000원이다.

#### 입력

- [ ] 구현에 필요한 상품 목록과 행사 목록을 파일 입출력을 통해 불러온다.
  - src/main/resources/products.md과 src/main/resources/promotions.md 파일을 이용한다.
  - 두 파일 모두 내용의 형식을 유지한다면 값은 수정할 수 있다.
- [ ] 구매할 상품과 수량을 입력 받는다. 상품명, 수량은 하이픈(-)으로, 개별 상품은 대괄호([])로 묶어 쉼표(,)로 구분한다.
  - ex. `[콜라-10],[사이다-3]`
  - 형식이 맞지 않을 경우, 예외를 던진다. (메시지 : `잘못된 입력입니다. 다시 입력해 주세요.`)
- [ ] 프로모션 적용이 가능한 상품에 대해 고객이 해당 수량보다 적게 가져온 경우, 그 수량만큼 추가 여부를 입력받는다.
  - `Y` or `N`
  - 이외 값을 입력할 경우, 예외를 던진다. (메시지 : `잘못된 입력입니다. 다시 입력해 주세요.`)
- [ ] 프로모션 재고가 부족하여 일부 수량을 프로모션 혜택 없이 결제해야 하는 경우, 일부 수량에 대해 정가로 결제할지 여부를 입력받는다.
  - `Y` or `N`
  - 이외 값을 입력할 경우, 예외를 던진다 (메시지 : `잘못된 입력입니다. 다시 입력해 주세요.`)
- [ ] 멤버십 할인 적용 여부를 입력 받는다.
  - `Y` or `N`
  - 이외 값을 입력할 경우, 예외를 던진다 (메시지 : `잘못된 입력입니다. 다시 입력해 주세요.`)
- [ ] 추가 구매 여부를 입력 받는다
  - `Y` or `N`
  - 이외 값을 입력할 경우, 예외를 던진다 (메시지 : `잘못된 입력입니다. 다시 입력해 주세요.`)

#### 출력
- [ ] 환영 인사와 함께 상품명, 가격, 프로모션 이름, 재고를 안내한다.
  - 만약 재고가 0개라면 재고 없음을 출력한다.
- [ ] 구매 상품 내역, 증정 상품 내역, 금액 정보를 출력한다.
