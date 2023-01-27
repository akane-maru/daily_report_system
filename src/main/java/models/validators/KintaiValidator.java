package models.validators;

import java.util.ArrayList;
import java.util.List;

import actions.views.KintaiView;
import constants.MessageConst;

/**
 * 勤怠インスタンスに設定されている値のバリデーションを行うクラス
 */
public class KintaiValidator {

    /**
     * 勤怠インスタンスの各項目についてバリデーションを行う
     * @param kv 勤怠インスタンス
     * @return エラーのリスト
     */
    public static List<String> validate(KintaiView kv) {
        List<String> errors = new ArrayList<String>();

        //出勤のチェック
        String begin_error = validateBegin(kv.getBegin());
        if (!begin_error.equals("")) {
            errors.add(begin_error);
        }

        //退勤のチェック
        String finish_error = validateFinish(kv.getFinish());
        if (!finish_error.equals("")) {
            errors.add(finish_error);
        }

        return errors;
    }

    /**
     * 出勤に入力値があるかをチェックし、入力値がなければエラーメッセージを返却
     * @param begin 出勤
     * @return エラーメッセージ
     */
    private static String validateBegin(String begin) {
        if (begin == null || begin.equals("")) {
            return MessageConst.E_NOCONTENT.getMessage();
        }

        //入力値がある場合は空文字を返却
        return "";
    }

    /**
     * 退勤に入力値があるかをチェックし、入力値がなければエラーメッセージを返却
     * @param finish 退勤
     * @return エラーメッセージ
     */
    private static String validateFinish(String finish) {
        if (finish == null || finish.equals("")) {
            return MessageConst.E_NOCONTENT.getMessage();
        }

        //入力値がある場合は空文字を返却
        return "";
    }
}