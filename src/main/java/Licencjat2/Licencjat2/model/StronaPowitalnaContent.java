package Licencjat2.Licencjat2.model;

import javax.persistence.*;

@Entity
@Table(name = "Content")
public class StronaPowitalnaContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contentId;
    @Column(nullable = false,length = 255)
    private String content;


    public Long getContentId() {
        return contentId;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
