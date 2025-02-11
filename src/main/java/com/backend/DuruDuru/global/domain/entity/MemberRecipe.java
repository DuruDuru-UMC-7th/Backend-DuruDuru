package com.backend.DuruDuru.global.domain.entity;

import com.backend.DuruDuru.global.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemberRecipe extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_recipe_id", nullable = false, columnDefinition = "bigint")
    private Long memberRecipeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "recipe_name", nullable = false , columnDefinition = "varchar(50)")
    private String recipeName;

    public void setMember(Member member) {
        this.member = member;
        if (!member.getMemberRecipes().contains(this)) {
            member.getMemberRecipes().add(this);
        }
    }

}
