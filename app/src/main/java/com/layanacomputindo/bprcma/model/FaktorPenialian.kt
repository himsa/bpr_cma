package com.layanacomputindo.bprcma.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class FaktorPenialian {
    @SerializedName("id")
    @Expose
    private var id: Int? = null
    @SerializedName("jaminan_tanah_bangunan_id")
    @Expose
    private var jaminanTanahBangunanId: Int? = null
    @SerializedName("ada_jalan_akses_masuk")
    @Expose
    private var adaJalanAksesMasuk: Int? = null
    @SerializedName("akses_jalan_roda_4")
    @Expose
    private var aksesJalanRoda4: Int? = null
    @SerializedName("akses_berupa_tanah_batu")
    @Expose
    private var aksesBerupaTanahBatu: Int? = null
    @SerializedName("dekat_sungai")
    @Expose
    private var dekatSungai: Int? = null
    @SerializedName("jauh_tempat_ibadah")
    @Expose
    private var jauhTempatIbadah: Int? = null
    @SerializedName("dekat_lokasi_banjir")
    @Expose
    private var dekatLokasiBanjir: Int? = null
    @SerializedName("dekat_tegangan_listrik_tinggi")
    @Expose
    private var dekatTeganganListrikTinggi: Int? = null
    @SerializedName("tanah_perlu_diurug")
    @Expose
    private var tanahPerluDiurug: Int? = null
    @SerializedName("mengikuti_master_plan_pemda")
    @Expose
    private var mengikutiMasterPlanPemda: Int? = null
    @SerializedName("lokasi_tusuk_sate")
    @Expose
    private var lokasiTusukSate: Int? = null
    @SerializedName("lokasi_tempat_ibadah")
    @Expose
    private var lokasiTempatIbadah: Int? = null
    @SerializedName("lokasi_tempat_usaha")
    @Expose
    private var lokasiTempatUsaha: Int? = null
    @SerializedName("dekat_makam")
    @Expose
    private var dekatMakam: Int? = null
    @SerializedName("dekat_spbu")
    @Expose
    private var dekatSpbu: Int? = null
    @SerializedName("dekat_tpa_sampah")
    @Expose
    private var dekatTpaSampah: Int? = null
    @SerializedName("dekat_sawah")
    @Expose
    private var dekatSawah: Int? = null
    @SerializedName("dekat_perindustrian")
    @Expose
    private var dekatPerindustrian: Int? = null
    @SerializedName("dekat_bahaya_longsor")
    @Expose
    private var dekatBahayaLongsor: Int? = null
    @SerializedName("kondisi_lingkungan_prospek")
    @Expose
    private var kondisiLingkunganProspek: String? = null
    @SerializedName("created_at")
    @Expose
    private var createdAt: String? = null
    @SerializedName("updated_at")
    @Expose
    private var updatedAt: String? = null

    fun getId(): Int? {
        return id
    }

    fun setId(id: Int?) {
        this.id = id
    }

    fun getJaminanTanahBangunanId(): Int? {
        return jaminanTanahBangunanId
    }

    fun setJaminanTanahBangunanId(jaminanTanahBangunanId: Int?) {
        this.jaminanTanahBangunanId = jaminanTanahBangunanId
    }

    fun getAdaJalanAksesMasuk(): Int? {
        return adaJalanAksesMasuk
    }

    fun setAdaJalanAksesMasuk(adaJalanAksesMasuk: Int?) {
        this.adaJalanAksesMasuk = adaJalanAksesMasuk
    }

    fun getAksesJalanRoda4(): Int? {
        return aksesJalanRoda4
    }

    fun setAksesJalanRoda4(aksesJalanRoda4: Int?) {
        this.aksesJalanRoda4 = aksesJalanRoda4
    }

    fun getAksesBerupaTanahBatu(): Int? {
        return aksesBerupaTanahBatu
    }

    fun setAksesBerupaTanahBatu(aksesBerupaTanahBatu: Int?) {
        this.aksesBerupaTanahBatu = aksesBerupaTanahBatu
    }

    fun getDekatSungai(): Int? {
        return dekatSungai
    }

    fun setDekatSungai(dekatSungai: Int?) {
        this.dekatSungai = dekatSungai
    }

    fun getJauhTempatIbadah(): Int? {
        return jauhTempatIbadah
    }

    fun setJauhTempatIbadah(jauhTempatIbadah: Int?) {
        this.jauhTempatIbadah = jauhTempatIbadah
    }

    fun getDekatLokasiBanjir(): Int? {
        return dekatLokasiBanjir
    }

    fun setDekatLokasiBanjir(dekatLokasiBanjir: Int?) {
        this.dekatLokasiBanjir = dekatLokasiBanjir
    }

    fun getDekatTeganganListrikTinggi(): Int? {
        return dekatTeganganListrikTinggi
    }

    fun setDekatTeganganListrikTinggi(dekatTeganganListrikTinggi: Int?) {
        this.dekatTeganganListrikTinggi = dekatTeganganListrikTinggi
    }

    fun getTanahPerluDiurug(): Int? {
        return tanahPerluDiurug
    }

    fun setTanahPerluDiurug(tanahPerluDiurug: Int?) {
        this.tanahPerluDiurug = tanahPerluDiurug
    }

    fun getMengikutiMasterPlanPemda(): Int? {
        return mengikutiMasterPlanPemda
    }

    fun setMengikutiMasterPlanPemda(mengikutiMasterPlanPemda: Int?) {
        this.mengikutiMasterPlanPemda = mengikutiMasterPlanPemda
    }

    fun getLokasiTusukSate(): Int? {
        return lokasiTusukSate
    }

    fun setLokasiTusukSate(lokasiTusukSate: Int?) {
        this.lokasiTusukSate = lokasiTusukSate
    }

    fun getLokasiTempatIbadah(): Int? {
        return lokasiTempatIbadah
    }

    fun setLokasiTempatIbadah(lokasiTempatIbadah: Int?) {
        this.lokasiTempatIbadah = lokasiTempatIbadah
    }

    fun getLokasiTempatUsaha(): Int? {
        return lokasiTempatUsaha
    }

    fun setLokasiTempatUsaha(lokasiTempatUsaha: Int?) {
        this.lokasiTempatUsaha = lokasiTempatUsaha
    }

    fun getDekatMakam(): Int? {
        return dekatMakam
    }

    fun setDekatMakam(dekatMakam: Int?) {
        this.dekatMakam = dekatMakam
    }

    fun getDekatSpbu(): Int? {
        return dekatSpbu
    }

    fun setDekatSpbu(dekatSpbu: Int?) {
        this.dekatSpbu = dekatSpbu
    }

    fun getDekatTpaSampah(): Int? {
        return dekatTpaSampah
    }

    fun setDekatTpaSampah(dekatTpaSampah: Int?) {
        this.dekatTpaSampah = dekatTpaSampah
    }

    fun getDekatSawah(): Int? {
        return dekatSawah
    }

    fun setDekatSawah(dekatSawah: Int?) {
        this.dekatSawah = dekatSawah
    }

    fun getDekatPerindustrian(): Int? {
        return dekatPerindustrian
    }

    fun setDekatPerindustrian(dekatPerindustrian: Int?) {
        this.dekatPerindustrian = dekatPerindustrian
    }

    fun getDekatBahayaLongsor(): Int? {
        return dekatBahayaLongsor
    }

    fun setDekatBahayaLongsor(dekatBahayaLongsor: Int?) {
        this.dekatBahayaLongsor = dekatBahayaLongsor
    }

    fun getKondisiLingkunganProspek(): String? {
        return kondisiLingkunganProspek
    }

    fun setKondisiLingkunganProspek(kondisiLingkunganProspek: String) {
        this.kondisiLingkunganProspek = kondisiLingkunganProspek
    }

    fun getCreatedAt(): String? {
        return createdAt
    }

    fun setCreatedAt(createdAt: String) {
        this.createdAt = createdAt
    }

    fun getUpdatedAt(): String? {
        return updatedAt
    }

    fun setUpdatedAt(updatedAt: String) {
        this.updatedAt = updatedAt
    }
}