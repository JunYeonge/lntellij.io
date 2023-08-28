package com.bed.entity;

import com.bed.dto.CommentDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Comment extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "increment", strategy = "increment")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "as_form_id")
    private AsForm asForm;
    private String nickname;
    private String body;
    public static Comment createComment(CommentDto dto, AsForm asForm){
        if(dto.getId()!=null){
            throw new IllegalArgumentException("댓글생성 실패");
        }
        if(dto.getAsFormId()!=asForm.getId()){
            System.out.println("asFormId: " + dto.getAsFormId());
            System.out.println("asForm ID: " + asForm.getId());
            throw new IllegalArgumentException("댓글생성실패!asFormId와 asForm의 ID가 일치하지 않습니다.");
        }

        return new Comment(
                dto.getId(),asForm,dto.getNickname(),dto.getBody());
    }
    public void patch(CommentDto dto) {
        if(this.id!=dto.getId()){
            throw new IllegalArgumentException("댓글수정실패! 잘못된 id가 입력됨");
        }
        if(dto.getNickname()!=null){
            this.nickname = dto.getNickname();
        }
        if(dto.getBody()!=null){
            this.body = dto.getBody();
        }
    }
}
