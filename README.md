# 개발환경
* 언어 : Java(Spring Framework)
* DB : OracleDB
* IDE : Spring Tool 4

# DB 구조
BOARD_POST
 - POST_ID(PK)
 - TITLE
 - CONTENT
 - CREATED_AT
 - AUTHOR_NAME

BOARD_RELATED_POST
  - RELATED_POST_ID(PK)
  - WORD
 
BOARD_RELATED_POST_FREQUENCY
  - ID
  - RELATED_POST_ID(FK)
  - POST_ID(FK)
  - FREQUENCY

## 시퀀스
- SEQ_ID
- SEQ_POST_ID
- SEQ_RELATED_POST_ID


# 수정 필요 부분
- 기존 게시글 내부에서의 단어 빈도만을 기준을 잡고 있었지만, "전체 글에 대한 단어 빈도"와 "게시글에 대한 단어 빈도" 모두 고려를 해야한다
- 즉, 이전의 기준으로만 로직을 만들다보니, 한 게시글에서만 빈도가 높은 유니크한 게시글을 걸러내지 못하는 현상이 발생하며, 특정 게시글에서 나오는 단어만을 기준으로 할 시 로직에 따라 비슷한 오류를 범할 수 있다는 피드백 받음
- 추가 : 추천 리스트 게시글 중복 현상 해결, 전체 글에 대한 단어 빈도 체크 코드 추가(추가 테스트 필요)
