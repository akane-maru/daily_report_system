package services;

import java.time.LocalDateTime;
import java.util.List;

import actions.views.EmployeeConverter;
import actions.views.EmployeeView;
import actions.views.KintaiConverter;
import actions.views.KintaiView;
import constants.JpaConst;
import models.Kintai;
import models.validators.KintaiValidator;

//勤怠テーブルの操作に関わる処理を行うクラス

public class KintaiService extends ServiceBase {

    //指定した従業員が勤怠登録した時間のデータを、指定されたページ数の一覧画面に表示する分取得し、
    //KintaiViewのリストで返却する
    //@param employee 従業員
    //@param page ページ数
    //@return 一覧画面に表示するデータのリスト
    public List<KintaiView> getMinePerPage(EmployeeView employee, int page) {

        List<Kintai> kintai = em.createNamedQuery(JpaConst.Q_KINTAI_GET_ALL_MINE, Kintai.class)
                .setParameter(JpaConst.JPQL_PARM_EMPLOYEE, EmployeeConverter.toModel(employee))
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page - 1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();
        return KintaiConverter.toViewList(kintai);
    }

    //指定した従業員が勤怠登録した時間のデータの件数を取得し、返却する
    //@param employee
    //@return 勤怠データの件数
    public long countAllMine(EmployeeView employee) {

        long count = (long) em.createNamedQuery(JpaConst.Q_KINTAI_COUNT_ALL_MINE, Long.class)
                .setParameter(JpaConst.JPQL_PARM_EMPLOYEE, EmployeeConverter.toModel(employee))
                .getSingleResult();
        return count;
    }

    //指定されたページ数の一覧画面に表示する勤怠データを取得し、KintaiViewのリストで返却する
    //@param page ページ数
    //@return 一覧画面に表示するデータのリスト
    public List<KintaiView> getAllPerPage(int page) {

        List<Kintai> kintai = em.createNamedQuery(JpaConst.Q_KINTAI_GET_ALL, Kintai.class)
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page - 1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();
        return KintaiConverter.toViewList(kintai);
    }

    //勤怠テーブルのデータの件数を取得し、返却する
    //@return データの件数
    public long countAll() {
        long kintai_count = (long) em.createNamedQuery(JpaConst.Q_KINTAI_COUNT, Long.class)
                .getSingleResult();
        return kintai_count;
    }

    //idを条件に取得したデータをKintaiViewのインスタンスで返却する
    //@param id
    //@return 取得データのインスタンス
    public KintaiView findOne(int id) {
        return KintaiConverter.toView(findOneInternal_f(id));
    }

     //画面から入力された勤怠の登録内容を元にデータを1件作成し、勤怠テーブルに登録する
     //@param kv 勤怠の登録内容
     //@return バリデーションで発生したエラーのリスト
    public List<String> create(KintaiView kv) {
        List<String> errors = KintaiValidator.validate(kv);
        if (errors.size() == 0) {
            LocalDateTime ldt = LocalDateTime.now();
            kv.setCreated_at(ldt);
            kv.setUpdated_at(ldt);
            createInternal(kv);
        }

        //バリデーションで発生したエラーを返却（エラーがなければ0件の空リスト）
        return errors;
    }

     //画面から入力された勤怠の登録内容を元に、日報データを更新する
     //@param kv 勤怠の更新内容
     //@return バリデーションで発生したエラーのリスト
    public List<String> update(KintaiView kv) {

        //バリデーションを行う
        List<String> errors = KintaiValidator.validate(kv);

        if (errors.size() == 0) {

            //更新日時を現在時刻に設定
            LocalDateTime ldt = LocalDateTime.now();
            kv.setUpdated_at(ldt);

            updateInternal(kv);
        }

        //バリデーションで発生したエラーを返却（エラーがなければ0件の空リスト）
        return errors;
    }

     //idを条件にデータを1件取得する
     //@param id
     //@return 取得データのインスタンス
    private Kintai findOneInternal_f(int id) {
        return em.find(Kintai.class, id);
    }

    //勤怠データを1件登録する
    //@param kv 勤怠データ
    private void createInternal(KintaiView kv) {
         em.getTransaction().begin();
         em.persist(KintaiConverter.toModel(kv));
         em.getTransaction().commit();
    }

    //勤怠データを更新する
    //@param kv 勤怠データ
    private void updateInternal(KintaiView kv) {

        em.getTransaction().begin();
        Kintai k = findOneInternal_f(kv.getId());
        KintaiConverter.copyViewToModel(k, kv);
        em.getTransaction().commit();
    }

}
