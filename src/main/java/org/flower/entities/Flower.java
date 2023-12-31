package org.flower.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.*;

import org.flower.models.recommend.flower.FlowerInfo;

@Data @Builder          // @Builder : 빌더 패턴을 사용하여 객체를 생성할 수 있게 한다.
@NoArgsConstructor      // 파라미터가 없는 기본 생성자를 생성
@AllArgsConstructor     // 모든 필드를 파라미터로 갖는 생성자를 생성
@Entity                 // 이 클래스가 JPA 엔티티임을 나타낸다. 즉 이 클래스의 인스턴스들은 데이터베이스의 레코드와 매핑된다.
@Table(name="flowers")    // 'flowers' 속성으로 해당 엔티티가 매핑될 테이블의 이름을 지정함
public class Flower {
    /*
     * @ID
     * 'flowerNo' 필드가 이 엔티티의 기본 키임을 나타낸다.
     *
     * @GeneratedValue(strategy = GenerationType.IDENTITY)
     * 기본키의 값이 데이터베이스에 자동으로 생성되도록 지정한다.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long flowerNo;            // 꽃 식별자

    @Column(nullable = false, length = 20)
    private String flowerNm;       // 꽃 이름

    @Column(nullable = false, length = 255)
    private String flowerMean;          // 꽃말

    @Column(length = 65)
    private String bloomseason;          // 개화 시기(월별)

    @Column(length = 10)
    private String season;               // 봄, 여름, 가을, 겨울

    @Column(length = 255)
    private String flowerIamges;   // 꽃 추천 시 뜨는 이미지

    @OneToMany(mappedBy = "flower", cascade = CascadeType.ALL, orphanRemoval = true) // FlowerKeywordMapping 엔티티에 있는 flower 필드에 매핑되어 있다는 것을 나타냄
    private List<FlowerWeight> flowerWeights = new ArrayList<>();

    // 좋아요 수를 저장할 필드
    @Column(nullable = false, columnDefinition = "int default 0")
    private int likes = 0; // 초기값은 0으로 설정

    // 새로 추가된 필드
    @Column(length = 50, nullable = false)
    private String englishNm = "a"; // 영문 이름 필드, 기본값 "a"

    public Flower(FlowerInfo flowerInfo) {
        this.flowerNm = flowerInfo.getFlowerNm();
        this.englishNm = flowerInfo.getEnglishNm();
        this.flowerMean = flowerInfo.getFlowerMean();
        this.bloomseason = flowerInfo.getBloomseason();
        this.season = flowerInfo.getSeason();
        this.flowerIamges = flowerInfo.getFlowerIamges();
        this.likes = flowerInfo.getLikes();

    }


}




