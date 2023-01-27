package actions.views;

import java.util.ArrayList;
import java.util.List;

import models.Kintai;

//勤怠データのDTOモデル⇔Viewモデルの変換を行うクラス

public class KintaiConverter {

    //ViewモデルのインスタンスからDTOモデルのインスタンスを作成する
    //@param bfv KintaiViewのインスタンス
    //@return Kintaiのインスタンス

    public static Kintai toModel(KintaiView kv) {
        return new Kintai(
                kv.getId(),
                EmployeeConverter.toModel(kv.getEmployee()),
                kv.getKintai_date(),
                kv.getBegin(),
                kv.getFinish(),
                kv.getCreated_at(),
                kv.getUpdated_at());
    }


    //DTOモデルのインスタンスからViewモデルのインスタンスを作成する
    //@param k Kintaiのインスタンス
    //@return KintaiViewのインスタンス

    public static KintaiView toView(Kintai k) {

        if (k == null) {
            return null;
        }

        return new KintaiView(
                k.getId(),
                EmployeeConverter.toView(k.getEmployee()),
                k.getKintai_date(),
                k.getBegin(),
                k.getFinish(),
                k.getCreated_at(),
                k.getUpdated_at());
    }

    //DTOモデルのリストからViewモデルのリストを作成する
    //@param list DTOモデルのリスト
    //@return Viewモデルのリスト
    public static List<KintaiView> toViewList(List<Kintai> list) {
        List<KintaiView> evs = new ArrayList<>();

        for (Kintai k : list) {
            evs.add(toView(k));
        }

        return evs;
    }

    //Viewモデルの全フィールドの内容をDTOモデルのフィールドにコピーする
    //@param k DTOモデル(コピー先)
    //@return kv Viewモデル(コピー元)
    public static void copyViewToModel(Kintai k, KintaiView kv) {
        k.setId(kv.getId());
        k.setEmployee(EmployeeConverter.toModel(kv.getEmployee()));
        k.setKintai_date(kv.getKintai_date());
        k.setBegin(kv.getBegin());
        k.setFinish(kv.getFinish());
        k.setCreated_at(kv.getCreated_at());
        k.setUpdated_at(kv.getUpdated_at());
    }

}
