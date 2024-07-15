package com.sparta.trello.card.entity;

import com.sparta.trello.auth.entity.Role;
import com.sparta.trello.auth.entity.User;
import com.sparta.trello.auth.entity.Role;
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
import lombok.Setter;


@Entity
@Getter
@Setter
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

  @ManyToOne(fetch = FetchType.LAZY)
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

  @OneToMany(mappedBy = "card", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private List<Comment> comments = new ArrayList<>();

  public Card(CardCreateRequestDto requestDto, Columns column, User user) {
    this.title = requestDto.getTitle();
    this.cardStatus = column.getCategory();
    this.content = requestDto.getContent();
    this.dueDate = requestDto.getDueDate();
    this.columns = column;
    this.board = column.getBoard();
    this.user = user;
  }

  public void updateCard(CardUpdateRequestDto requestDto, User user) {
    this.title = requestDto.getTitle();
    this.content = requestDto.getContent();
    this.dueDate = requestDto.getDueDate();
    this.user = user;
  }

  public void updateCardStatus(Columns column, CardUpdateCardStatusRequestDto requestDto) {
    this.columns = column;
    this.cardStatus = CategoryEnum.valueOf(requestDto.getCardStatus());
  }

  public void updatePosition(int newPosition) {
    this.position = newPosition;
  }

  public boolean checkUser(User user) {
    if (!this.user.getRole().equals(Role.MANAGER) && !this.user.getId().equals(user.getId())) {
      throw new IllegalArgumentException("해당 카드의 수정/삭제 권한이 없는 유저입니다.");
    } return true;
  }
}
