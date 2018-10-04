package com.layanacomputindo.bprcma.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class TataRuang {
    @SerializedName("id")
    @Expose
    private var id: Int? = null
    @SerializedName("jaminan_tanah_bangunan_id")
    @Expose
    private var jaminanTanahBangunanId: Int? = null
    @SerializedName("ruang_makan_keluarga")
    @Expose
    private var ruangMakanKeluarga: String? = null
    @SerializedName("kamar_tidur_lantai_1")
    @Expose
    private var kamarTidurLantai1: Int? = null
    @SerializedName("kamar_tidur_lantai_2")
    @Expose
    private var kamarTidurLantai2: Int? = null
    @SerializedName("kamar_tidur_lantai_3")
    @Expose
    private var kamarTidurLantai3: Int? = null
    @SerializedName("kamar_mandi_lantai_1")
    @Expose
    private var kamarMandiLantai1: Int? = null
    @SerializedName("kamar_mandi_lantai_2")
    @Expose
    private var kamarMandiLantai2: Int? = null
    @SerializedName("kamar_mandi_lantai_3")
    @Expose
    private var kamarMandiLantai3: Int? = null
    @SerializedName("ruang_makan_keluarga_lantai_1")
    @Expose
    private var ruangMakanKeluargaLantai1: Int? = null
    @SerializedName("ruang_makan_keluarga_lantai_2")
    @Expose
    private var ruangMakanKeluargaLantai2: Int? = null
    @SerializedName("ruang_makan_keluarga_lantai_3")
    @Expose
    private var ruangMakanKeluargaLantai3: Int? = null
    @SerializedName("ruang_tamu_lantai_1")
    @Expose
    private var ruangTamuLantai1: Int? = null
    @SerializedName("ruang_tamu_lantai_2")
    @Expose
    private var ruangTamuLantai2: Int? = null
    @SerializedName("ruang_tamu_lantai_3")
    @Expose
    private var ruangTamuLantai3: Int? = null
    @SerializedName("dapur_lantai_1")
    @Expose
    private var dapurLantai1: Int? = null
    @SerializedName("dapur_lantai_2")
    @Expose
    private var dapurLantai2: Int? = null
    @SerializedName("dapur_lantai_3")
    @Expose
    private var dapurLantai3: Int? = null
    @SerializedName("garasi_lantai_1")
    @Expose
    private var garasiLantai1: Int? = null
    @SerializedName("garasi_lantai_2")
    @Expose
    private var garasiLantai2: Int? = null
    @SerializedName("garasi_lantai_3")
    @Expose
    private var garasiLantai3: Int? = null
    @SerializedName("kamar_pembantu_lantai_1")
    @Expose
    private var kamarPembantuLantai1: Int? = null
    @SerializedName("kamar_pembantu_lantai_2")
    @Expose
    private var kamarPembantuLantai2: Int? = null
    @SerializedName("kamar_pembantu_lantai_3")
    @Expose
    private var kamarPembantuLantai3: Int? = null
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

    fun getRuangMakanKeluarga(): String? {
        return ruangMakanKeluarga
    }

    fun setRuangMakanKeluarga(ruangMakanKeluarga: String) {
        this.ruangMakanKeluarga = ruangMakanKeluarga
    }

    fun getKamarTidurLantai1(): Int? {
        return kamarTidurLantai1
    }

    fun setKamarTidurLantai1(kamarTidurLantai1: Int?) {
        this.kamarTidurLantai1 = kamarTidurLantai1
    }

    fun getKamarTidurLantai2(): Int? {
        return kamarTidurLantai2
    }

    fun setKamarTidurLantai2(kamarTidurLantai2: Int?) {
        this.kamarTidurLantai2 = kamarTidurLantai2
    }

    fun getKamarTidurLantai3(): Int? {
        return kamarTidurLantai3
    }

    fun setKamarTidurLantai3(kamarTidurLantai3: Int?) {
        this.kamarTidurLantai3 = kamarTidurLantai3
    }

    fun getKamarMandiLantai1(): Int? {
        return kamarMandiLantai1
    }

    fun setKamarMandiLantai1(kamarMandiLantai1: Int?) {
        this.kamarMandiLantai1 = kamarMandiLantai1
    }

    fun getKamarMandiLantai2(): Int? {
        return kamarMandiLantai2
    }

    fun setKamarMandiLantai2(kamarMandiLantai2: Int?) {
        this.kamarMandiLantai2 = kamarMandiLantai2
    }

    fun getKamarMandiLantai3(): Int? {
        return kamarMandiLantai3
    }

    fun setKamarMandiLantai3(kamarMandiLantai3: Int?) {
        this.kamarMandiLantai3 = kamarMandiLantai3
    }

    fun getRuangMakanKeluargaLantai1(): Int? {
        return ruangMakanKeluargaLantai1
    }

    fun setRuangMakanKeluargaLantai1(ruangMakanKeluargaLantai1: Int?) {
        this.ruangMakanKeluargaLantai1 = ruangMakanKeluargaLantai1
    }

    fun getRuangMakanKeluargaLantai2(): Int? {
        return ruangMakanKeluargaLantai2
    }

    fun setRuangMakanKeluargaLantai2(ruangMakanKeluargaLantai2: Int?) {
        this.ruangMakanKeluargaLantai2 = ruangMakanKeluargaLantai2
    }

    fun getRuangMakanKeluargaLantai3(): Int? {
        return ruangMakanKeluargaLantai3
    }

    fun setRuangMakanKeluargaLantai3(ruangMakanKeluargaLantai3: Int?) {
        this.ruangMakanKeluargaLantai3 = ruangMakanKeluargaLantai3
    }

    fun getRuangTamuLantai1(): Int? {
        return ruangTamuLantai1
    }

    fun setRuangTamuLantai1(ruangTamuLantai1: Int?) {
        this.ruangTamuLantai1 = ruangTamuLantai1
    }

    fun getRuangTamuLantai2(): Int? {
        return ruangTamuLantai2
    }

    fun setRuangTamuLantai2(ruangTamuLantai2: Int?) {
        this.ruangTamuLantai2 = ruangTamuLantai2
    }

    fun getRuangTamuLantai3(): Int? {
        return ruangTamuLantai3
    }

    fun setRuangTamuLantai3(ruangTamuLantai3: Int?) {
        this.ruangTamuLantai3 = ruangTamuLantai3
    }

    fun getDapurLantai1(): Int? {
        return dapurLantai1
    }

    fun setDapurLantai1(dapurLantai1: Int?) {
        this.dapurLantai1 = dapurLantai1
    }

    fun getDapurLantai2(): Int? {
        return dapurLantai2
    }

    fun setDapurLantai2(dapurLantai2: Int?) {
        this.dapurLantai2 = dapurLantai2
    }

    fun getDapurLantai3(): Int? {
        return dapurLantai3
    }

    fun setDapurLantai3(dapurLantai3: Int?) {
        this.dapurLantai3 = dapurLantai3
    }

    fun getGarasiLantai1(): Int? {
        return garasiLantai1
    }

    fun setGarasiLantai1(garasiLantai1: Int?) {
        this.garasiLantai1 = garasiLantai1
    }

    fun getGarasiLantai2(): Int? {
        return garasiLantai2
    }

    fun setGarasiLantai2(garasiLantai2: Int?) {
        this.garasiLantai2 = garasiLantai2
    }

    fun getGarasiLantai3(): Int? {
        return garasiLantai3
    }

    fun setGarasiLantai3(garasiLantai3: Int?) {
        this.garasiLantai3 = garasiLantai3
    }

    fun getKamarPembantuLantai1(): Int? {
        return kamarPembantuLantai1
    }

    fun setKamarPembantuLantai1(kamarPembantuLantai1: Int?) {
        this.kamarPembantuLantai1 = kamarPembantuLantai1
    }

    fun getKamarPembantuLantai2(): Int? {
        return kamarPembantuLantai2
    }

    fun setKamarPembantuLantai2(kamarPembantuLantai2: Int?) {
        this.kamarPembantuLantai2 = kamarPembantuLantai2
    }

    fun getKamarPembantuLantai3(): Int? {
        return kamarPembantuLantai3
    }

    fun setKamarPembantuLantai3(kamarPembantuLantai3: Int?) {
        this.kamarPembantuLantai3 = kamarPembantuLantai3
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