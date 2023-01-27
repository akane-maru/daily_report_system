package models;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import constants.JpaConst;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 出退勤データのDTOモデル
@Table(name = JpaConst.TABLE_KINTAI)
@NamedQueries({
    @NamedQuery(
            name = JpaConst.Q_KINTAI_GET_ALL,
            query = JpaConst.Q_KINTAI_GET_ALL_DEF),
    @NamedQuery(
            name = JpaConst.Q_KINTAI_COUNT,
            query = JpaConst.Q_KINTAI_COUNT_DEF),
    @NamedQuery(
            name = JpaConst.Q_KINTAI_GET_ALL_MINE,
            query = JpaConst.Q_KINTAI_GET_ALL_MINE_DEF),
    @NamedQuery(
            name = JpaConst.Q_KINTAI_COUNT_ALL_MINE,
            query = JpaConst.Q_KINTAI_COUNT_ALL_MINE_DEF)
})


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Kintai {

    //id
    @Id
    @Column(name = JpaConst.KINTAI_COL_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;



    //勤怠を登録した従業員
    @ManyToOne
    @JoinColumn(name = JpaConst.KINTAI_COL_EMP, nullable = false)
    private Employee employee;


    //いつの勤怠かを示す日付
    @Column(name = JpaConst.KINTAI_COL_DATE,nullable = false)
    private LocalDate kintai_date;


    //出勤
    @Column(name = JpaConst.KINTAI_BEGIN_COL_DATE, nullable = false)
    private String begin;

    //退勤
    @Column(name = JpaConst.KINTAI_FINISH_COL_DATE, nullable = false)
    private String finish;

    //登録日時
    @Column(name = JpaConst.KINTAI_COL_CREATED_AT, nullable = false)
    private LocalDateTime created_at;

    //更新日時
    @Column(name = JpaConst.KINTAI_COL_UPDATED_AT,nullable = false)
    private LocalDateTime updated_at;

}
