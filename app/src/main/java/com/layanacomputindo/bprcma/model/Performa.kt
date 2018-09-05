package com.layanacomputindo.bprcma.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class Performa {
    @SerializedName("id")
    @Expose
    private var id: Int? = null
    @SerializedName("analisa_keuangan_id")
    @Expose
    private var analisaKeuanganId: Int? = null
    @SerializedName("omzet")
    @Expose
    private var omzet: Int? = null
    @SerializedName("keterangan_omzet")
    @Expose
    private var keteranganOmzet: String? = null
    @SerializedName("hpp")
    @Expose
    private var hpp: String? = null
    @SerializedName("gaji_karyawan")
    @Expose
    private var gajiKaryawan: Int? = null
    @SerializedName("sewa_tempat_usaha")
    @Expose
    private var sewaTempatUsaha: Int? = null
    @SerializedName("listrik_air_telepon_usaha")
    @Expose
    private var listrikAirTeleponUsaha: Int? = null
    @SerializedName("admin_keamanan_lingkungan")
    @Expose
    private var adminKeamananLingkungan: Int? = null
    @SerializedName("biaya_lain")
    @Expose
    private var biayaLain: Int? = null
    @SerializedName("jumlah_biaya_usaha")
    @Expose
    private var jumlahBiayaUsaha: Int? = null
    @SerializedName("keterangan_biaya")
    @Expose
    private var keteranganBiaya: String? = null
    @SerializedName("keuntungan_usaha")
    @Expose
    private var keuntunganUsaha: Int? = null
    @SerializedName("gross_profit_margin")
    @Expose
    private var grossProfitMargin: String? = null
    @SerializedName("pendapatan_pasangan")
    @Expose
    private var pendapatanPasangan: Int? = null
    @SerializedName("pendapatan_lain")
    @Expose
    private var pendapatanLain: Int? = null
    @SerializedName("keterangan_pendapatan_lain")
    @Expose
    private var keteranganPendapatanLain: String? = null
    @SerializedName("total_income_netto")
    @Expose
    private var totalIncomeNetto: Int? = null
    @SerializedName("keterangan_total_income_netto")
    @Expose
    private var keteranganTotalIncomeNetto: String? = null
    @SerializedName("biaya_rumah_tangga")
    @Expose
    private var biayaRumahTangga: Int? = null
    @SerializedName("listrik_air_telepon_rumah_tangga")
    @Expose
    private var listrikAirTeleponRumahTangga: Int? = null
    @SerializedName("biaya_pendidikan")
    @Expose
    private var biayaPendidikan: Int? = null
    @SerializedName("biaya_sewa_lainnya")
    @Expose
    private var biayaSewaLainnya: Int? = null
    @SerializedName("total_biaya_pribadi")
    @Expose
    private var totalBiayaPribadi: Int? = null
    @SerializedName("keterangan_keuangan_pribadi")
    @Expose
    private var keteranganKeuanganPribadi: String? = null
    @SerializedName("sisa_penghasilan_bersih")
    @Expose
    private var sisaPenghasilanBersih: Int? = null
    @SerializedName("keterangan_sisa_penghasilan_bersih")
    @Expose
    private var keteranganSisaPenghasilanBersih: String? = null
    @SerializedName("angsuran_bank_lain")
    @Expose
    private var angsuranBankLain: Int? = null
    @SerializedName("rencana_angsuran_cma")
    @Expose
    private var rencanaAngsuranCma: Int? = null
    @SerializedName("total_angsuran")
    @Expose
    private var totalAngsuran: Int? = null
    @SerializedName("keterangan_angsuran")
    @Expose
    private var keteranganAngsuran: String? = null
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

    fun getOmzet(): Int? {
        return omzet
    }

    fun setOmzet(omzet: Int?) {
        this.omzet = omzet
    }

    fun getKeteranganOmzet(): String? {
        return keteranganOmzet
    }

    fun setKeteranganOmzet(keteranganOmzet: String) {
        this.keteranganOmzet = keteranganOmzet
    }

    fun getHpp(): String? {
        return hpp
    }

    fun setHpp(hpp: String) {
        this.hpp = hpp
    }

    fun getGajiKaryawan(): Int? {
        return gajiKaryawan
    }

    fun setGajiKaryawan(gajiKaryawan: Int?) {
        this.gajiKaryawan = gajiKaryawan
    }

    fun getSewaTempatUsaha(): Int? {
        return sewaTempatUsaha
    }

    fun setSewaTempatUsaha(sewaTempatUsaha: Int?) {
        this.sewaTempatUsaha = sewaTempatUsaha
    }

    fun getListrikAirTeleponUsaha(): Int? {
        return listrikAirTeleponUsaha
    }

    fun setListrikAirTeleponUsaha(listrikAirTeleponUsaha: Int?) {
        this.listrikAirTeleponUsaha = listrikAirTeleponUsaha
    }

    fun getAdminKeamananLingkungan(): Int? {
        return adminKeamananLingkungan
    }

    fun setAdminKeamananLingkungan(adminKeamananLingkungan: Int?) {
        this.adminKeamananLingkungan = adminKeamananLingkungan
    }

    fun getBiayaLain(): Int? {
        return biayaLain
    }

    fun setBiayaLain(biayaLain: Int?) {
        this.biayaLain = biayaLain
    }

    fun getJumlahBiayaUsaha(): Int? {
        return jumlahBiayaUsaha
    }

    fun setJumlahBiayaUsaha(jumlahBiayaUsaha: Int?) {
        this.jumlahBiayaUsaha = jumlahBiayaUsaha
    }

    fun getKeteranganBiaya(): String? {
        return keteranganBiaya
    }

    fun setKeteranganBiaya(keteranganBiaya: String) {
        this.keteranganBiaya = keteranganBiaya
    }

    fun getKeuntunganUsaha(): Int? {
        return keuntunganUsaha
    }

    fun setKeuntunganUsaha(keuntunganUsaha: Int?) {
        this.keuntunganUsaha = keuntunganUsaha
    }

    fun getGrossProfitMargin(): String? {
        return grossProfitMargin
    }

    fun setGrossProfitMargin(grossProfitMargin: String) {
        this.grossProfitMargin = grossProfitMargin
    }

    fun getPendapatanPasangan(): Int? {
        return pendapatanPasangan
    }

    fun setPendapatanPasangan(pendapatanPasangan: Int?) {
        this.pendapatanPasangan = pendapatanPasangan
    }

    fun getPendapatanLain(): Int? {
        return pendapatanLain
    }

    fun setPendapatanLain(pendapatanLain: Int?) {
        this.pendapatanLain = pendapatanLain
    }

    fun getKeteranganPendapatanLain(): String? {
        return keteranganPendapatanLain
    }

    fun setKeteranganPendapatanLain(keteranganPendapatanLain: String) {
        this.keteranganPendapatanLain = keteranganPendapatanLain
    }

    fun getTotalIncomeNetto(): Int? {
        return totalIncomeNetto
    }

    fun setTotalIncomeNetto(totalIncomeNetto: Int?) {
        this.totalIncomeNetto = totalIncomeNetto
    }

    fun getKeteranganTotalIncomeNetto(): String? {
        return keteranganTotalIncomeNetto
    }

    fun setKeteranganTotalIncomeNetto(keteranganTotalIncomeNetto: String) {
        this.keteranganTotalIncomeNetto = keteranganTotalIncomeNetto
    }

    fun getBiayaRumahTangga(): Int? {
        return biayaRumahTangga
    }

    fun setBiayaRumahTangga(biayaRumahTangga: Int?) {
        this.biayaRumahTangga = biayaRumahTangga
    }

    fun getListrikAirTeleponRumahTangga(): Int? {
        return listrikAirTeleponRumahTangga
    }

    fun setListrikAirTeleponRumahTangga(listrikAirTeleponRumahTangga: Int?) {
        this.listrikAirTeleponRumahTangga = listrikAirTeleponRumahTangga
    }

    fun getBiayaPendidikan(): Int? {
        return biayaPendidikan
    }

    fun setBiayaPendidikan(biayaPendidikan: Int?) {
        this.biayaPendidikan = biayaPendidikan
    }

    fun getBiayaSewaLainnya(): Int? {
        return biayaSewaLainnya
    }

    fun setBiayaSewaLainnya(biayaSewaLainnya: Int?) {
        this.biayaSewaLainnya = biayaSewaLainnya
    }

    fun getTotalBiayaPribadi(): Int? {
        return totalBiayaPribadi
    }

    fun setTotalBiayaPribadi(totalBiayaPribadi: Int?) {
        this.totalBiayaPribadi = totalBiayaPribadi
    }

    fun getKeteranganKeuanganPribadi(): String? {
        return keteranganKeuanganPribadi
    }

    fun setKeteranganKeuanganPribadi(keteranganKeuanganPribadi: String) {
        this.keteranganKeuanganPribadi = keteranganKeuanganPribadi
    }

    fun getSisaPenghasilanBersih(): Int? {
        return sisaPenghasilanBersih
    }

    fun setSisaPenghasilanBersih(sisaPenghasilanBersih: Int?) {
        this.sisaPenghasilanBersih = sisaPenghasilanBersih
    }

    fun getKeteranganSisaPenghasilanBersih(): String? {
        return keteranganSisaPenghasilanBersih
    }

    fun setKeteranganSisaPenghasilanBersih(keteranganSisaPenghasilanBersih: String) {
        this.keteranganSisaPenghasilanBersih = keteranganSisaPenghasilanBersih
    }

    fun getAngsuranBankLain(): Int? {
        return angsuranBankLain
    }

    fun setAngsuranBankLain(angsuranBankLain: Int?) {
        this.angsuranBankLain = angsuranBankLain
    }

    fun getRencanaAngsuranCma(): Int? {
        return rencanaAngsuranCma
    }

    fun setRencanaAngsuranCma(rencanaAngsuranCma: Int?) {
        this.rencanaAngsuranCma = rencanaAngsuranCma
    }

    fun getTotalAngsuran(): Int? {
        return totalAngsuran
    }

    fun setTotalAngsuran(totalAngsuran: Int?) {
        this.totalAngsuran = totalAngsuran
    }

    fun getKeteranganAngsuran(): String? {
        return keteranganAngsuran
    }

    fun setKeteranganAngsuran(keteranganAngsuran: String) {
        this.keteranganAngsuran = keteranganAngsuran
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