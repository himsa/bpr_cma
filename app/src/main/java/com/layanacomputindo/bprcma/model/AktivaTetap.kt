package com.layanacomputindo.bprcma.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class AktivaTetap {
    @SerializedName("id")
    @Expose
    private var id: Int? = null
    @SerializedName("analisa_keuangan_id")
    @Expose
    private var analisaKeuanganId: Int? = null
    @SerializedName("tanah")
    @Expose
    private var tanah: Int? = null
    @SerializedName("bangunan")
    @Expose
    private var bangunan: Int? = null
    @SerializedName("kendaraan")
    @Expose
    private var kendaraan: Int? = null
    @SerializedName("inventaris_lain")
    @Expose
    private var inventarisLain: Int? = null
    @SerializedName("akumulasi_depresiasi")
    @Expose
    private var akumulasiDepresiasi: Int? = null
    @SerializedName("sub_jumlah")
    @Expose
    private var subJumlah: Int? = null
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

    fun getAnalisaKeuanganId(): Int? {
        return analisaKeuanganId
    }

    fun setAnalisaKeuanganId(analisaKeuanganId: Int?) {
        this.analisaKeuanganId = analisaKeuanganId
    }

    fun getTanah(): Int? {
        return tanah
    }

    fun setTanah(tanah: Int?) {
        this.tanah = tanah
    }

    fun getBangunan(): Int? {
        return bangunan
    }

    fun setBangunan(bangunan: Int?) {
        this.bangunan = bangunan
    }

    fun getKendaraan(): Int? {
        return kendaraan
    }

    fun setKendaraan(kendaraan: Int?) {
        this.kendaraan = kendaraan
    }

    fun getInventarisLain(): Int? {
        return inventarisLain
    }

    fun setInventarisLain(inventarisLain: Int?) {
        this.inventarisLain = inventarisLain
    }

    fun getAkumulasiDepresiasi(): Int? {
        return akumulasiDepresiasi
    }

    fun setAkumulasiDepresiasi(akumulasiDepresiasi: Int?) {
        this.akumulasiDepresiasi = akumulasiDepresiasi
    }

    fun getSubJumlah(): Int? {
        return subJumlah
    }

    fun setSubJumlah(subJumlah: Int?) {
        this.subJumlah = subJumlah
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