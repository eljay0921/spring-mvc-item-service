package hello.itemservice.domain.member;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Member {

    private long id;

    @NotEmpty(message = "아이디를 입력해주세요.")
    private String loginId;
    @NotEmpty(message = "이름을 입력해주세요.")
    private String name;
    @NotEmpty(message = "비밀번호를 입력해주세요.")
    @Size(min = 6, message = "비밀번호는 최소 {min}자 이상이어야 합니다.")
    private String password;
}
