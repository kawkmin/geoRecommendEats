package com.wanted.domain.restaurant.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 맛집의 소재지 정보를 담는 엔티티
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class RestaurantSite {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "restaurant_site_id")
  private Long id;

  // 소재지 도로명 주소
  @Column(nullable = false)
  private String roadNameAddress;

  // 소재지 지번 주소
  @Column(nullable = false)
  private String lotNumberAddress;

  // 소재지 우편번호
  @Column(nullable = false)
  private Integer zipCode;

  // 소재지 면적
  @Column(nullable = false)
  private Long area;

  // Restaurnt 테이블이 부모 테이블
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "restaurant_id")
  private Restaurant restaurant;
}
