package actions;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.EmployeeView;
import actions.views.KintaiView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import constants.MessageConst;
import services.KintaiService;

//勤怠に関する処理を行うActionクラス

public class KintaiAction extends ActionBase {

    private KintaiService service_k;

    //メソッドを実行する
    @Override
    public void process() throws ServletException, IOException {

        service_k = new KintaiService();

        //メソッドを実行
        invoke();
        service_k.close();
    }

    //一覧画面を表示する
    //@throws ServletException
    //@throws IOException
    public void index() throws ServletException, IOException {

        //指定されたページ数の一覧画面に表示する出勤データを取得
        int page = getPage();
        List<KintaiView> kintai = service_k.getAllPerPage(page);

        //全出勤データの件数を取得
        long kintai_count = service_k.countAll();

        putRequestScope(AttributeConst.KINTAI, kintai); //取得した勤怠データ
        putRequestScope(AttributeConst.KINTAI_COUNT, kintai_count); //全ての勤怠データの件数
        putRequestScope(AttributeConst.PAGE, page); //ページ数
        putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE); //1ページに表示するレコードの数

        //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
        String flush = getSessionScope(AttributeConst.FLUSH);
        if (flush != null) {
            putRequestScope(AttributeConst.FLUSH, flush);
            removeSessionScope(AttributeConst.FLUSH);
        }

        //一覧画面を表示
        forward(ForwardConst.FW_KINTAI_INDEX);

    }

    //新規登録画面を表示する
    //@throws ServletException
    //@throws IOEXception
    public void entryNew() throws ServletException, IOException {

        putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン

        //勤怠情報の空インスタンスに、勤怠の日付＝今日の日付を設定する
        KintaiView kv = new KintaiView();
        kv.setKintai_date(LocalDate.now());
        putRequestScope(AttributeConst.KINTAI, kv); //日付のみ設定済の勤怠インスタンス

        // 新規登録画面を表示
        forward(ForwardConst.FW_KINTAI_NEW);

    }

    // 新規登録を行う
    //@throws ServletException
    //@throws IOException
    public void create() throws ServletException, IOException {

        //CSRF対策　トークンのチェック
        if (checkToken()) {

            //勤怠の日付が入力されていなければ、今日の日付を設定
            LocalDate day = null;
            if (getRequestParam(AttributeConst.KINTAI_DATE) == null
                    || getRequestParam(AttributeConst.KINTAI_DATE).equals("")) {
                day = LocalDate.now();
            } else {
                day = LocalDate.parse(getRequestParam(AttributeConst.KINTAI_DATE));
            }

            //セッションからログイン中の従業員情報を取得
            EmployeeView ev = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);

            //パラメータの値をもとに出退勤情報のインスタンスを作成する
            KintaiView kv = new KintaiView(
                    null,
                    ev, //ログインしている従業員を、勤怠登録者として登録する
                    day,
                    getRequestParam(AttributeConst.KINTAI_BEGIN),
                    getRequestParam(AttributeConst.KINTAI_FINISH),
                    null,
                    null);

            //勤怠情報登録
            List<String> errors = service_k.create(kv);

            if (errors.size() > 0) {
                //登録中にエラーがあった場合
                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                putRequestScope(AttributeConst.KINTAI, kv); //入力された勤怠情報
                putRequestScope(AttributeConst.ERR, errors); //エラーのリスト

                //新規登録画面を再表示
                forward(ForwardConst.FW_KINTAI_NEW);

            } else {
                //登録中にエラーがなかった場合
                //セッションに登録完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_REGISTERED.getMessage());

                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_KINTAI, ForwardConst.CMD_INDEX);
            }
        }

    }

    //詳細画面を表示する
    //@throws ServletException
    //@throws IOException
    public void show() throws ServletException, IOException {

        System.out.println("show start");

        //idを条件に出勤データを取得する
        KintaiView kv = service_k.findOne(toNumber(getRequestParam(AttributeConst.KINTAI_ID)));

        if (kv == null) {
            //該当のデータが存在しない場合はエラー画面を表示
            forward(ForwardConst.FW_ERR_UNKNOWN);

        } else {
            putRequestScope(AttributeConst.KINTAI, kv); //取得した勤怠データ

            //詳細画面を表示
            forward(ForwardConst.FW_KINTAI_SHOW);
        }

    }

    //編集画面を表示する
    //@throws ServletException
    //@throws IOException
    public void edit() throws ServletException, IOException {
        //idを条件に勤怠データを取得する
        KintaiView kv = service_k.findOne(toNumber(getRequestParam(AttributeConst.KINTAI_ID)));

        //セッションからログイン中の従業員情報を取得
        EmployeeView ev = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);

        if (kv == null || ev.getId() != kv.getEmployee().getId()) {
            //該当のデータが存在しない、または
            //ログインしている従業員が勤怠の登録者でない場合はエラー画面を表示
            forward(ForwardConst.FW_ERR_UNKNOWN);

        } else {
            putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
            putRequestScope(AttributeConst.KINTAI, kv); //取得した勤怠データ

            //編集画面を表示
            forward(ForwardConst.FW_KINTAI_EDIT);

        }

    }

    //更新を行う
    //@throws ServletException
    //@throws IOException
    public void update() throws ServletException, IOException {

        //CSRF対策　トークンのチェック
        if (checkToken()) {

            //idを条件に勤怠データを取得する
            KintaiView kv = service_k.findOne(toNumber(getRequestParam(AttributeConst.KINTAI_ID)));

            //入力された勤怠情報を設定する
            kv.setKintai_date(toLocalDate(getRequestParam(AttributeConst.KINTAI_DATE)));
            kv.setBegin(getRequestParam(AttributeConst.KINTAI_BEGIN));
            kv.setFinish(getRequestParam(AttributeConst.KINTAI_FINISH));

            //勤怠データを更新する
            List<String> errors = service_k.update(kv);

            if (errors.size() > 0) {

                //更新中にエラーが発生した場合
                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                putRequestScope(AttributeConst.KINTAI, kv); //入力された勤怠情報
                putRequestScope(AttributeConst.ERR, errors); //エラーのリスト

                //編集画面を表示
                forward(ForwardConst.FW_KINTAI_EDIT);

            } else {
                //更新中にエラーがなかった場合
                //セッションに更新完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_UPDATED.getMessage());

                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_KINTAI, ForwardConst.CMD_INDEX);
            }

        }
    }

}
