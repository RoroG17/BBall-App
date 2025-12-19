package com.example.bball.models


data class Stat(
    val id: Int,
    val Id_Match: Int,
    val licence: String,
    val minutes: String,

    // Q1
    val q1_passes_decisives: Int,
    val q1_rebonds_offensifs: Int,
    val q1_rebonds_defensifs: Int,
    val q1_interceptions: Int,
    val q1_contres: Int,
    val q1_ballons_perdus: Int,
    val q1_fautes: Int,
    val q1_tirs_2pts_reussis: Int,
    val q1_tirs_2pts_manques: Int,
    val q1_tirs_3pts_reussis: Int,
    val q1_tirs_3pts_manques: Int,
    val q1_lancers_francs_reussis: Int,
    val q1_lancers_francs_rates: Int,
    val q1_passes_reussies: Int,
    val q1_passes_rates: Int,

    // Q2
    val q2_passes_decisives: Int,
    val q2_rebonds_offensifs: Int,
    val q2_rebonds_defensifs: Int,
    val q2_interceptions: Int,
    val q2_contres: Int,
    val q2_ballons_perdus: Int,
    val q2_fautes: Int,
    val q2_tirs_2pts_reussis: Int,
    val q2_tirs_2pts_manques: Int,
    val q2_tirs_3pts_reussis: Int,
    val q2_tirs_3pts_manques: Int,
    val q2_lancers_francs_reussis: Int,
    val q2_lancers_francs_rates: Int,
    val q2_passes_reussies: Int,
    val q2_passes_rates: Int,

    // Q3
    val q3_passes_decisives: Int,
    val q3_rebonds_offensifs: Int,
    val q3_rebonds_defensifs: Int,
    val q3_interceptions: Int,
    val q3_contres: Int,
    val q3_ballons_perdus: Int,
    val q3_fautes: Int,
    val q3_tirs_2pts_reussis: Int,
    val q3_tirs_2pts_manques: Int,
    val q3_tirs_3pts_reussis: Int,
    val q3_tirs_3pts_manques: Int,
    val q3_lancers_francs_reussis: Int,
    val q3_lancers_francs_rates: Int,
    val q3_passes_reussies: Int,
    val q3_passes_rates: Int,

    // Q4
    val q4_passes_decisives: Int,
    val q4_rebonds_offensifs: Int,
    val q4_rebonds_defensifs: Int,
    val q4_interceptions: Int,
    val q4_contres: Int,
    val q4_ballons_perdus: Int,
    val q4_fautes: Int,
    val q4_tirs_2pts_reussis: Int,
    val q4_tirs_2pts_manques: Int,
    val q4_tirs_3pts_reussis: Int,
    val q4_tirs_3pts_manques: Int,
    val q4_lancers_francs_reussis: Int,
    val q4_lancers_francs_rates: Int,
    val q4_passes_reussies: Int,
    val q4_passes_rates: Int,

    val match_libelle: String
)
{

    private fun sum(vararg values: Int) = values.sum()
    private fun pct(made: Int, attempted: Int): Float =
        if (attempted > 0) 100f * made / attempted else 0f

    // --- Calculs en getter (pas stockés) ---
    val points: Int
        get() = q1_tirs_2pts_reussis * 2 + q1_tirs_3pts_reussis * 3 + q1_lancers_francs_reussis +
                q2_tirs_2pts_reussis * 2 + q2_tirs_3pts_reussis * 3 + q2_lancers_francs_reussis +
                q3_tirs_2pts_reussis * 2 + q3_tirs_3pts_reussis * 3 + q3_lancers_francs_reussis +
                q4_tirs_2pts_reussis * 2 + q4_tirs_3pts_reussis * 3 + q4_lancers_francs_reussis

    val passesDecisives: Int
        get() = q1_passes_decisives + q2_passes_decisives + q3_passes_decisives + q4_passes_decisives

    val rebondsOff: Int
        get() = sum(q1_rebonds_offensifs, q2_rebonds_offensifs, q3_rebonds_offensifs, q4_rebonds_offensifs)

    val rebondsDef: Int
        get() = sum(q1_rebonds_defensifs, q2_rebonds_defensifs, q3_rebonds_defensifs, q4_rebonds_defensifs)

    val rebonds: Int
        get() = rebondsOff + rebondsDef

    val interceptions: Int
        get() = sum(q1_interceptions, q2_interceptions, q3_interceptions, q4_interceptions)

    val contres: Int
        get() = sum(q1_contres, q2_contres, q3_contres, q4_contres)

    val ballonsPerdus: Int
        get() = sum(q1_ballons_perdus, q2_ballons_perdus, q3_ballons_perdus, q4_ballons_perdus)

    val fautes: Int
        get() = sum(q1_fautes, q2_fautes, q3_fautes, q4_fautes)

    val passesReussies: Int
        get() = sum(q1_passes_reussies, q2_passes_reussies, q3_passes_reussies, q4_passes_reussies)

    val passesRates: Int
        get() = sum(q1_passes_rates, q2_passes_rates, q3_passes_rates, q4_passes_rates)

    val tirs2Reussis: Int
        get() = sum(q1_tirs_2pts_reussis, q2_tirs_2pts_reussis, q3_tirs_2pts_reussis, q4_tirs_2pts_reussis)

    val tirs2Manques: Int
        get() = sum(q1_tirs_2pts_manques, q2_tirs_2pts_manques, q3_tirs_2pts_manques, q4_tirs_2pts_manques)

    val tirs2Tentes: Int
        get() = tirs2Reussis + tirs2Manques

    val pct2: Float
        get() = pct(tirs2Reussis, tirs2Tentes)

    val tirs3Reussis: Int
        get() = sum(q1_tirs_3pts_reussis, q2_tirs_3pts_reussis, q3_tirs_3pts_reussis, q4_tirs_3pts_reussis)

    val tirs3Manques: Int
        get() = sum(q1_tirs_3pts_manques, q2_tirs_3pts_manques, q3_tirs_3pts_manques, q4_tirs_3pts_manques)

    val tirs3Tentes: Int
        get() = tirs3Reussis + tirs3Manques

    val pct3: Float
        get() = pct(tirs3Reussis, tirs3Tentes)

    val lfReussis: Int
        get() = sum(q1_lancers_francs_reussis, q2_lancers_francs_reussis, q3_lancers_francs_reussis, q4_lancers_francs_reussis)

    val lfRates: Int
        get() = sum(q1_lancers_francs_rates, q2_lancers_francs_rates, q3_lancers_francs_rates, q4_lancers_francs_rates)

    val lfTentes: Int
        get() = lfReussis + lfRates

    val pctLF: Float
        get() = pct(lfReussis, lfTentes)

    val tirsTentesTotal: Int
        get() = tirs2Tentes + tirs3Tentes

    val tirsReussisTotal: Int
        get() = tirs2Reussis + tirs3Reussis

    val pctTirGlobal: Float
        get() = pct(tirsReussisTotal, tirsTentesTotal)

    val negatives: Int
        get() = ballonsPerdus + fautes

    val ratioAssistTurnover: Float
        get() = if (ballonsPerdus > 0) passesDecisives.toFloat() / ballonsPerdus else passesDecisives.toFloat()

    val efficacite: Int
        get() = points + rebonds + passesDecisives + interceptions + contres - ballonsPerdus - tirsTentesTotal
}
