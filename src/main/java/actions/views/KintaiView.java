package actions.views;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//勤怠について画面の入力値・出力値を扱うViewモデル
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class KintaiView {

    //id
    private Integer id;

    //勤怠を登録した従業員
    private EmployeeView employee;

    //いつの勤怠かを示す日付
    private LocalDate kintai_date;

    //出勤
    private String begin;

    //退勤
    private String finish;

    //登録日時
    private LocalDateTime created_at;

    //登録日時
    private LocalDateTime updated_at;

}
