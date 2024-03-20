package ru.nikita.weatherappdiplom.utils

import org.junit.Assert

import org.junit.Test

class MoonPhasesTest {

    @Test
    fun changeMoonPhasesExpectedNewMoon() {
        val cameWord = MoonPhases().changeMoonPhases("New Moon")
        val moonPhaseFromString = ru.nikita.weatherappdiplom.R.string.new_moon
        Assert.assertEquals(cameWord, moonPhaseFromString)
    }

    @Test
    fun changeMoonPhasesExpectedWaxingCrescent() {
        val cameWord = MoonPhases().changeMoonPhases("Waxing Crescent")
        val moonPhaseFromString = ru.nikita.weatherappdiplom.R.string.waxing_crescent
        Assert.assertEquals(cameWord, moonPhaseFromString)
    }

    @Test
    fun changeMoonPhasesExpectedFirstQuarter() {
        val cameWord = MoonPhases().changeMoonPhases("First Quarter")
        val moonPhaseFromString = ru.nikita.weatherappdiplom.R.string.first_quarter
        Assert.assertEquals(cameWord, moonPhaseFromString)
    }
    @Test
    fun changeMoonPhasesExpectedWaxingGibbous() {
        val cameWord = MoonPhases().changeMoonPhases("Waxing Gibbous")
        val moonPhaseFromString = ru.nikita.weatherappdiplom.R.string.waxing_gibbous
        Assert.assertEquals(cameWord, moonPhaseFromString)
    }
    @Test
    fun changeMoonPhasesExpectedFullMoon() {
        val cameWord = MoonPhases().changeMoonPhases("Full Moon")
        val moonPhaseFromString = ru.nikita.weatherappdiplom.R.string.full_moon
        Assert.assertEquals(cameWord, moonPhaseFromString)
    }
    @Test
    fun changeMoonPhasesExpectedWaningGibbous() {
        val cameWord = MoonPhases().changeMoonPhases("Waning Gibbous")
        val moonPhaseFromString = ru.nikita.weatherappdiplom.R.string.waning_gibbous
        Assert.assertEquals(cameWord, moonPhaseFromString)
    }
    @Test
    fun changeMoonPhasesExpectedLastQuarter() {
        val cameWord = MoonPhases().changeMoonPhases("Last Quarter")
        val moonPhaseFromString = ru.nikita.weatherappdiplom.R.string.last_quarter
        Assert.assertEquals(cameWord, moonPhaseFromString)
    }
    @Test
    fun changeMoonPhasesExpectedWaningCrescent() {
        val cameWord = MoonPhases().changeMoonPhases("Waning Crescent")
        val moonPhaseFromString = ru.nikita.weatherappdiplom.R.string.waning_crescent
        Assert.assertEquals(cameWord, moonPhaseFromString)
    }
    @Test
    fun changeMoonPhasesExpectedElse() {
        val cameWord = MoonPhases().changeMoonPhases("test")
        val moonPhaseFromString = ru.nikita.weatherappdiplom.R.string.error_moon_phase
        Assert.assertEquals(cameWord, moonPhaseFromString)
    }
    @Test
    fun setMoonImageExpectedNewMoon() {
        val cameWord = MoonPhases().setMoonImage("New Moon")
        val moonImageFromString = ru.nikita.weatherappdiplom.R.drawable.new_moon
        Assert.assertEquals(cameWord, moonImageFromString)
    }
    @Test
    fun setMoonImageExpectedWaxingCrescent() {
        val cameWord = MoonPhases().setMoonImage("Waxing Crescent")
        val moonImageFromString = ru.nikita.weatherappdiplom.R.drawable.waxing_crescent
        Assert.assertEquals(cameWord, moonImageFromString)
    }
    @Test
    fun setMoonImageExpectedFirstQuarter() {
        val cameWord = MoonPhases().setMoonImage("First Quarter")
        val moonImageFromString = ru.nikita.weatherappdiplom.R.drawable.first_quarter
        Assert.assertEquals(cameWord, moonImageFromString)
    }
    @Test
    fun setMoonImageExpectedWaxingGibbous() {
        val cameWord = MoonPhases().setMoonImage("Waxing Gibbous")
        val moonImageFromString = ru.nikita.weatherappdiplom.R.drawable.waxing_gibbous
        Assert.assertEquals(cameWord, moonImageFromString)
    }
    @Test
    fun setMoonImageExpectedFullMoon() {
        val cameWord = MoonPhases().setMoonImage("Full Moon")
        val moonImageFromString = ru.nikita.weatherappdiplom.R.drawable.full_moon
        Assert.assertEquals(cameWord, moonImageFromString)
    }
    @Test
    fun setMoonImageExpectedWaningGibbous() {
        val cameWord = MoonPhases().setMoonImage("Waning Gibbous")
        val moonImageFromString = ru.nikita.weatherappdiplom.R.drawable.waning_gibbous
        Assert.assertEquals(cameWord, moonImageFromString)
    }
    @Test
    fun setMoonImageExpectedLastQuarter() {
        val cameWord = MoonPhases().setMoonImage("Last Quarter")
        val moonImageFromString = ru.nikita.weatherappdiplom.R.drawable.last_quarter
        Assert.assertEquals(cameWord, moonImageFromString)
    }
    @Test
    fun setMoonImageExpectedWaningCrescent() {
        val cameWord = MoonPhases().setMoonImage("Waning Crescent")
        val moonImageFromString = ru.nikita.weatherappdiplom.R.drawable.waning_crescent
        Assert.assertEquals(cameWord, moonImageFromString)
    }
    @Test
    fun setMoonImageExpectedElse() {
        val cameWord = MoonPhases().setMoonImage("test")
        val moonImageFromString = ru.nikita.weatherappdiplom.R.drawable.ic_error_24
        Assert.assertEquals(cameWord, moonImageFromString)
    }
}