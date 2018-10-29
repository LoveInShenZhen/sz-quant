package sz.tushare.record.dict

//
// Created by kk on 2018/10/27.
//

/**
 * 主要报表类型说明
 * 参考: https://tushare.pro/document/2?doc_id=33
 */
enum class ReportType(val code:Int, val typeName:String, val desc:String) {

    HeBingBaoBiao(1, "合并报表", "上市公司最新报表（默认）"),
    DanJiHeBing(2, "单季合并", "单一季度的合并报表"),
    TiaoZhengDanJiHeBingBiao(3, "调整单季合并表", "调整后的单季合并报表（如果有）"),
    TiaoZhengHeBingBaoBiao(4, "调整合并报表", "本年度公布上年同期的财务报表数据，报告期为上年度"),
    TiaoZhengQianHeBingBaoBiao(5, "调整前合并报表", "数据发生变更，将原数据进行保留，即调整前的原数据"),
    MuGongSiBaoBiao(6, "母公司报表", "该公司母公司的财务报表数据"),
    MuGongSiDanJiBiao(7, "母公司单季表", "母公司的单季度表"),
    MuGongSiTiaoZhengDanJiBiao(8, "母公司调整单季表", "母公司调整后的单季表"),
    MuGongSiTiaoZhengBiao(9, "母公司调整表", "该公司母公司的本年度公布上年同期的财务报表数据"),
    MuGongSiTiaoZhengQianBaoBiao(10, "母公司调整前报表", "母公司调整之前的原始财务报表数据"),
    TiaoZhengQianHeBingYuanBaoBiao(11, "调整前合并原报表", "调整之前合并报表原数据"),
    MuGongSiTiaoZhengQianYuanBaoBiao(12, "母公司调整前原报表", "母公司报表发生变更前保留的原数据")

}