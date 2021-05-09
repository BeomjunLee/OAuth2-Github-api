## OAuth2 Github 연동 및 Github Api 사용
자세한 내용은 블로그에 포스팅하였습니다 https://blog.naver.com/qjawnswkd/222293370027<br>

### Gradle
```java
//spring security oauth2
implementation 'org.springframework.boot:spring-boot-starter-oauth2-client'
implementation 'org.springframework.boot:spring-boot-starter-security'

//github api
implementation group: 'org.kohsuke', name: 'github-api', version: '1.125'
  ```
  
  <br>

### application.yml
```java
## OAuth2
spring:
  security:
    oauth2:
      client:
        registration:
          github:
            client-id: {클라이언트 id}
            client-secret: {클라이언트 secret}       
```

<br>

### Service OAuth2
```java
@Service
@RequiredArgsConstructor
public class CustomOAuth2MemberService implements OAuth2UserService{

    private final MemberRepository memberRepository;
    private final HttpSession session;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        DefaultOAuth2UserService service = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = service.loadUser(userRequest);

        Member member = saveOrUpdate(oAuth2User);

        session.setAttribute("oAuthToken", userRequest.getAccessToken().getTokenValue());

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(member.getRole())),
                oAuth2User.getAttributes(), "login");
        //nameAttributeKey = Principal name에 저장됨
    }

    public Member saveOrUpdate(OAuth2User oAuth2User) {
        Member oAuthMember = Member.builder()
                .username(oAuth2User.getAttribute("login"))
                .name(oAuth2User.getAttribute("name"))
                .picture(oAuth2User.getAttribute("avatar_url"))
                .role(MemberRole.USER)
                .build();

        Member member = memberRepository.findByUsername(oAuthMember.getUsername())
                .map(entity -> entity.update(oAuthMember))
                .orElse(oAuthMember);
        return memberRepository.save(member);
    }
}
```

<br>

### OAuth2 Result
<img src="https://user-images.githubusercontent.com/69130921/117583581-d8e86980-b142-11eb-88e2-855827ed6ece.PNG">
<img src="https://user-images.githubusercontent.com/69130921/117583584-dc7bf080-b142-11eb-99ba-5ed94514339d.png">
<img src="https://user-images.githubusercontent.com/69130921/117583587-e1d93b00-b142-11eb-99ba-009fb8e858e6.png">

<br>

### Github Api 사용 example
```java
//로그인시 얻어지는 accessToken 값과, 유저이름으로 Github api 접근
//GitHub를 통해 다양한 api 에 접근할 수 있다.
GitHub gitHub = new GitHubBuilder()
                .withOAuthToken(session.getAttribute("oAuthToken").toString(), oAuth2User.getName()).build(); 
                
//accessToken 이용 안할시 1시간에 50번만 호출 가능하고 이용시에는 5000번까지 가능하다		
GHUser user = gitHub.getUser(oAuth2User.getName()); 
//repo 이름으로 찾기 예시
GHRepository repository = user.getRepository("microservices-spring-cloud"); 
 
 //아이디 로그인 없이 그냥 불러올수 있다 (1시간 50번 요청 제한)
GitHub git = new GitHubBuilder().build(); 
//로그인 안할시에는 repo 검색시 앞에 유저이름이 필수로 들어가야한다.
GHRepository repository = git.getRepository("BeomjunLee/microservices-spring-cloud"); 

//등등 다양한 Github api 를 불러올수 있다.
```

<br>

몇가지 예제를 적자면 <br><br>

<img src="https://user-images.githubusercontent.com/69130921/117583739-d6d2da80-b143-11eb-8f22-18c1ef98cc28.png"><br>
GHUser 를 통해 다양한 api 요청 메서드에 접근할 수 있다.<br><br>
<img src="https://user-images.githubusercontent.com/69130921/117583780-0eda1d80-b144-11eb-9a99-6f62ed1fe408.png"><br>
특정 repo 에 접근해서 커밋과 전체 커밋 갯수를 확인할 수 있다.<br>
<img src="https://user-images.githubusercontent.com/69130921/117583799-26190b00-b144-11eb-9010-e115c187622d.png"><br>
<br>
<img src="https://user-images.githubusercontent.com/69130921/117583843-71331e00-b144-11eb-8e23-5bd7116356d0.png"><br>
특정 repo 의 기여자들 리스트와 커밋 현황을 확인할 수 있다.<br>
<img src="https://user-images.githubusercontent.com/69130921/117583864-88720b80-b144-11eb-89d7-14d92054e507.png"><br><br>
외에 다양한 api 를 사용할 수 있다.
<br>


