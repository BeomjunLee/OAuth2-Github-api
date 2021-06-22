package security5.oauth2.response;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import security5.oauth2.member.LoginController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class LoginResource extends EntityModel<Response>{
    public LoginResource(Response content, Link... links) {
        super(content, links);
        add(linkTo(LoginController.class).slash("login").withSelfRel());
    }
}
