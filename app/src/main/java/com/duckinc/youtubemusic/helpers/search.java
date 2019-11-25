class ShowRateUsLogicTest : Spek({
    // property declaration, setup and preparation are skipped
    describe("show rate us logic") {
        context("first conditions checks") {
            context("buy clicked once") {
                beforeEachTest {
                    prepareConditions(buyClickedTimes = 1)
                }

                it("should not show 'rate us'") {
                    Assert.assertFalse(showRateUsLogic.shouldShowRateUs())
                }
            }

            context("buy clicked two times") {
                beforeEachTest {
                    prepareConditions(buyClickedTimes = 2)
                }

                it("should show 'rate us'") {
                    Assert.assertTrue(showRateUsLogic.shouldShowRateUs())
                }
            }
        }

        context("'rate us' was shown already, and user clicked 'show me later' on the dialog") {
            context("less than two months passed and user clicks buy") {
                beforeEachTest {
                    prepareConditions(
                        buyClickedTimes = 3,
                        lastShownTimeMillis = SOME_DAY_IN_MILLIS,
                        currentTimeMillis = SOME_DAY_IN_MILLIS + LESS_THAN_TWO_MONTHS
                    )
                }

                it("should not show 'rate us' again") {
                    Assert.assertFalse(showRateUsLogic.shouldShowRateUs())
                }
            }

            context("more than two months passed and user clicks buy") {
                beforeEachTest {
                    prepareConditions(
                        buyClickedTimes = 3,
                        lastShownTimeMillis = SOME_DAY_IN_MILLIS,
                        currentTimeMillis = SOME_DAY_IN_MILLIS + MORE_THAN_TWO_MONTHS
                    )
                }

                it("should show 'rate us' again") {
                    Assert.assertTrue(showRateUsLogic.shouldShowRateUs())
                }
            }
        }
    }
})
