package com.sparta.trello.card.entity;

import com.sparta.trello.auth.entity.Role;
import com.sparta.trello.auth.entity.User;
import com.sparta.trello.board.entity.Board;
import com.sparta.trello.card.dto.CardCreateRequestDto;
import com.sparta.trello.card.dto.CardUpdateCardStatusRequestDto;
import com.sparta.trello.card.dto.CardUpdateRequestDto;
import com.sparta.trello.columns.entity.CategoryEnum;
import com.sparta.trello.columns.entity.Columns;
import com.sparta.trello.comment.entity.Comment;
import com.sparta.trello.common.Timestamped;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor
@Table(name = "cards")
public class Card extends Timestamped {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;
  private String content;
  private int position;

  @Column(name = "dueto_date")
  private String dueDate;

  @ManyToOne
  @JoinColumn(name = "column_id")
  private Columns columns;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne
  @JoinColumn(name = "board_id")
  private Board board;

  @Enumerated(EnumType.STRING)
  @Column(name= "card_status")
  private CategoryEnum cardStatus;

  @OneToMany(mappedBy = "card_id", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Comment> comments = new ArrayList<>();

  public Card(CardCreateRequestDto requestDto, Columns column, User user) {
    this.title = requestDto.getTitle();
    this.cardStatus = requestDto.getCardStatus();
    this.content = requestDto.getContent();
    this.dueDate = requestDto.getDueDate();
    this.columns = column;
    this.user = user;
  }

  public void updateCard(CardUpdateRequestDto requestDto) {
    this.title = requestDto.getTitle();
    this.content = requestDto.getContent();
    this.dueDate = requestDto.getDueDate();
  }

  public void updateCardStatus(CardUpdateCardStatusRequestDto requestDto) {
    this.cardStatus = requestDto.getNewCardStatus();
  }

  public boolean checkUser(User user) {
    if (!this.user.getRole().equals(Role.MANAGER) || !this.user.getId().equals(user.getId())) {
      throw new IllegalArgumentException("해당 카드의 수정/삭제 권한이 없는 유저입니다.");
    } return true;
  }
}
