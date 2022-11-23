# Sponsio

Sponsio, latin for bet, is a software tool used to scan Romanian bookies in order to find arbitrage betting opportunities.

## Arbitrage betting

Arbitrage betting is a form of betting that guarantees profit to the player no matter the outcome of a match.

This can happen due to bookies miss-aligning their odds creating an opportunity for the player to bet on different bookies
on the same game (on different outcomes) and coming out ahead no matter what the result is.

For this tool I'm using only both 2-way and 3-way arbitrage betting

## How it works

Let's take an example game (a classic h2h in the tennis world): Roger Federer VS Rafael Nadal

We are playing at Wimbledon so Federer is favoured to win normally. But the odds are pretty close. 
Nadal has been on a pretty good form lately. So we have the following scenario:

On Mozzart we have the following odds: Federer to win at 1.90 and Nadal to win at 2.10

On Betano we have the following odds: Federer to win at 2.05 and Nadal to win at 1.95

Now, if we bet $100 on Nadal on Mozzart and $100 on Federer on Betano we will always come out on top. 
If Nadal wins we get back 100 x 2.10 = $210.
If Federer wins we get back 100 x 2.05 = $205.

So we bet 200$ but are guaranteed to win $205 or $2010. Now this is pretty easy to spot by the eye, but things get complicated on other scenarios.

## Math Formula 2-way

The formula to determine if we have an arbitrage possibility is the following.

**(TOTAL_SUM / odd1) + (TOTAL_SUM / odd 2) = OUTLAY**

**If OUTLAY < TOTAL_SUM --> Arbitrage possibility**

Let's take another example. Federer plays Antoine Escoffier who is ranked 190 on ATP. Federer is heavily favoured to win.

On Betano Federer has 1.20 odds that he will win.

On Mozzart Antoine has 8.00 odds that he will win.

This is not easy to spot, but we have an arbitrage possibility. Let's assume we want to bet 1000$ in total.

In this example:

**($1000 ÷ 1.20) + ($1000 ÷ 8.00) = your total outlay**

Calculated as:

**$833.33 + $125.00 = $958.33**

You now know exactly how much you are outlaying ($958.33) and how much to bet at each bookie ($833.33 at Betano, and $125.00 at Mozzart).

Calculating your profit is just as easy:

**Winnings - Outlay = your total Profit**

**$1000 - $958.33 = $41.67**

And finally, to calculate your return on investment you simply divide your profit by the initial amount invested: 

**$41.67 ÷ $958.33 = 4.35%**

## Math formula 3-way

As with two-outcome betting, you can obtain a betting agency’s margin on their odds by summing the inverses of the odds. So if an agency offers the following odds for a soccer match:

```
Everton	   1.80
Draw	   3.35
Tottenham  4.40
```
The sum of the inverses of the odds is 1/1.80 + 1/3.35 + 1/4.40 = 1.081. The larger this figure, the greater the margin that the bookmaker is taking. Because the sum is greater than 1, if you placed equitable bets (i.e. providing the same profit) on all three outcomes, you would be guaranteed a loss due to this margin.

If you can find differing sets of odds for the same event, you may be able to come up with a combination of win, draw and loss bets that guarantees a profit, regardless of the event outcome. The different betting odds could be provided by different bookmakers, as in the example below, or it may be due to odds changing over time as the game or series progresses. Arbitrage opportunities will be discussed in more detail in upcoming posts.

Suppose two agencies offered the following odds:

```
                Agency 1	Agency 2
Everton	        1.80	        2.30
Draw	        3.35	        3.25
Tottenham	4.40	        2.95
```
If you sum the inverses of agency 2’s odds for an Everton win along with agency 1’s odds for a draw and an Everton loss, you get 1/2.30 + 1/3.35 + 1/4.40 = 0.961. This figure is below 1, so an arbitrage does opportunity exist. Note that a number of cross-combinations can be tested. To find the best combination, take the largest odds for each possible outcome.

To calculate the amount to bet on each outcome, determine the total amount you would like to bet. Then calculate the amounts to bet on each particular outcome as follows:

Definitions:

**b1 = bet (in dollars) on outcome 1 (Everton win).**

**b2 = bet (in dollars) on outcome 2 (draw)**

**b3 = bet (in dollars) on outcome 3 (Everton loss)**

**B = b1 + b2 + b3 = combined bet amount**

**o1 = odds for outcome 1 (Everton win).**

**o2 = odds for outcome 2 (draw)**

**o3 = odds for outcome 3 (Everton loss)**

In this example I will bet $1,000 in total. Calculate the bets for each outcome as follows:

**b1 = B / (1 + o1/o2 + o1/o3)**

**b2 = B / (1 + o2/o1 + o2/o3)**

**b3 = B / (1 + o3/o1 + o3/o2)**

**b1 = $1000 / (1 + 2.30/3.35 + 2.30/4.40) = $452.63**

**b2 = $1000 / (1 + 3.35/2.30 + 3.35/4.40) = $310.76**

**b3= $1000 / (1 + 4.40/2.30 + 4.40/3.35) = $236.60**

Each bet equals the total bet amount divided by 1 plus the sum of the ratios of that outcome’s odds to the other outcomes’ odds

Calculate the guaranteed profit as **b1o1 – B (or as b2o2 – B, etc)**

**$452.63 x 2.30 – $1000 = $41.06**

**$310.76 x 3.35 – $1000 = $41.06**

**$236.60 x 4.40 – $1000 = $41.06**

Betting $452.63 on an Everton win, $310.76 on a draw, and $236.60 on an Everton loss would guarantee a profit of $41.06, which is a 4.1% return on the total bets.

## Arbitrages found

### Football
```
2022-11-23 15:51:08.270  INFO 22912 --- [           main] eridanus.sponsio.runner.SponsioRunner    : Arbitrage opportunity found!
2022-11-23 15:51:08.270  INFO 22912 --- [           main] eridanus.sponsio.runner.SponsioRunner    : Germania vs Japonia
2022-11-23 15:51:08.271  INFO 22912 --- [           main] eridanus.sponsio.runner.SponsioRunner    : Bet: 
2022-11-23 15:51:08.273  INFO 22912 --- [           main] eridanus.sponsio.runner.SponsioRunner    : $204.18725005880972 on Germania to draw on Boookie Betano
2022-11-23 15:51:08.273  INFO 22912 --- [           main] eridanus.sponsio.runner.SponsioRunner    : $671.8419195483416 on Germania to win on Boookie Betano
2022-11-23 15:51:08.273  INFO 22912 --- [           main] eridanus.sponsio.runner.SponsioRunner    : $123.97083039284874 on Japonia to win on Boookie Betano
2022-11-23 15:51:08.274  INFO 22912 --- [           main] eridanus.sponsio.runner.SponsioRunner    : Guaranteed profit: 41.35497529992949

2022-11-23 15:51:08.274  INFO 22912 --- [           main] eridanus.sponsio.runner.SponsioRunner    : Arbitrage opportunity found!
2022-11-23 15:51:08.274  INFO 22912 --- [           main] eridanus.sponsio.runner.SponsioRunner    : Germania vs Japonia
2022-11-23 15:51:08.274  INFO 22912 --- [           main] eridanus.sponsio.runner.SponsioRunner    : Bet: 
2022-11-23 15:51:08.274  INFO 22912 --- [           main] eridanus.sponsio.runner.SponsioRunner    : $149.7876909561228 on Japonia to win on Boookie Betano
2022-11-23 15:51:08.275  INFO 22912 --- [           main] eridanus.sponsio.runner.SponsioRunner    : $202.74293523353995 on Japonia to draw on Boookie Betano
2022-11-23 15:51:08.275  INFO 22912 --- [           main] eridanus.sponsio.runner.SponsioRunner    : $647.4693738103372 on Germania to win on Boookie Betano
2022-11-23 15:51:08.275  INFO 22912 --- [           main] eridanus.sponsio.runner.SponsioRunner    : Guaranteed profit: 3.577529406022677

2022-11-23 15:51:08.275  INFO 22912 --- [           main] eridanus.sponsio.runner.SponsioRunner    : Arbitrage opportunity found!
2022-11-23 15:51:08.275  INFO 22912 --- [           main] eridanus.sponsio.runner.SponsioRunner    : Germania vs Japonia
2022-11-23 15:51:08.275  INFO 22912 --- [           main] eridanus.sponsio.runner.SponsioRunner    : Bet: 
2022-11-23 15:51:08.275  INFO 22912 --- [           main] eridanus.sponsio.runner.SponsioRunner    : $651.3534121235227 on Germania to win on Boookie Betano
2022-11-23 15:51:08.275  INFO 22912 --- [           main] eridanus.sponsio.runner.SponsioRunner    : $197.96035074342356 on Germania to draw on Boookie Betano
2022-11-23 15:51:08.275  INFO 22912 --- [           main] eridanus.sponsio.runner.SponsioRunner    : $150.68623713305374 on Japonia to win on Boookie Betano
2022-11-23 15:51:08.275  INFO 22912 --- [           main] eridanus.sponsio.runner.SponsioRunner    : Guaranteed profit: 9.597788791460289

2022-11-23 15:51:08.275  INFO 22912 --- [           main] eridanus.sponsio.runner.SponsioRunner    : Arbitrage opportunity found!
2022-11-23 15:51:08.275  INFO 22912 --- [           main] eridanus.sponsio.runner.SponsioRunner    : Germania vs Japonia
2022-11-23 15:51:08.275  INFO 22912 --- [           main] eridanus.sponsio.runner.SponsioRunner    : Bet: 
2022-11-23 15:51:08.275  INFO 22912 --- [           main] eridanus.sponsio.runner.SponsioRunner    : $217.78037969390317 on Germania to draw on Boookie Betano
2022-11-23 15:51:08.275  INFO 22912 --- [           main] eridanus.sponsio.runner.SponsioRunner    : $660.3663126202225 on Germania to win on Boookie Betano
2022-11-23 15:51:08.276  INFO 22912 --- [           main] eridanus.sponsio.runner.SponsioRunner    : $121.85330768587438 on Japonia to win on Boookie Betano
2022-11-23 15:51:08.276  INFO 22912 --- [           main] eridanus.sponsio.runner.SponsioRunner    : Guaranteed profit: 23.56778456134498

2022-11-23 15:51:08.276  INFO 22912 --- [           main] eridanus.sponsio.runner.SponsioRunner    : Arbitrage opportunity found!
2022-11-23 15:51:08.276  INFO 22912 --- [           main] eridanus.sponsio.runner.SponsioRunner    : Germania vs Japonia
2022-11-23 15:51:08.276  INFO 22912 --- [           main] eridanus.sponsio.runner.SponsioRunner    : Bet: 
2022-11-23 15:51:08.276  INFO 22912 --- [           main] eridanus.sponsio.runner.SponsioRunner    : $128.57681511584065 on Japonia to win on Boookie Betano
2022-11-23 15:51:08.276  INFO 22912 --- [           main] eridanus.sponsio.runner.SponsioRunner    : $207.8009133185303 on Japonia to draw on Boookie Betano
2022-11-23 15:51:08.276  INFO 22912 --- [           main] eridanus.sponsio.runner.SponsioRunner    : $663.622271565629 on Germania to win on Boookie Betano
2022-11-23 15:51:08.276  INFO 22912 --- [           main] eridanus.sponsio.runner.SponsioRunner    : Guaranteed profit: 28.614520926725163

2022-11-23 15:51:08.276  INFO 22912 --- [           main] eridanus.sponsio.runner.SponsioRunner    : Arbitrage opportunity found!
2022-11-23 15:51:08.276  INFO 22912 --- [           main] eridanus.sponsio.runner.SponsioRunner    : Germania vs Japonia
2022-11-23 15:51:08.276  INFO 22912 --- [           main] eridanus.sponsio.runner.SponsioRunner    : Bet: 
2022-11-23 15:51:08.276  INFO 22912 --- [           main] eridanus.sponsio.runner.SponsioRunner    : $656.3672863751418 on Germania to win on Boookie Betano
2022-11-23 15:51:08.276  INFO 22912 --- [           main] eridanus.sponsio.runner.SponsioRunner    : $216.4615518896744 on Germania to draw on Boookie Betano
2022-11-23 15:51:08.276  INFO 22912 --- [           main] eridanus.sponsio.runner.SponsioRunner    : $127.17116173518373 on Japonia to win on Boookie Betano
2022-11-23 15:51:08.276  INFO 22912 --- [           main] eridanus.sponsio.runner.SponsioRunner    : Guaranteed profit: 17.369293881469844

2022-11-23 15:51:08.276  INFO 22912 --- [           main] eridanus.sponsio.runner.SponsioRunner    : Arbitrage opportunity found!
2022-11-23 15:51:08.276  INFO 22912 --- [           main] eridanus.sponsio.runner.SponsioRunner    : Belgia vs Canada
2022-11-23 15:51:08.276  INFO 22912 --- [           main] eridanus.sponsio.runner.SponsioRunner    : Bet: 
2022-11-23 15:51:08.276  INFO 22912 --- [           main] eridanus.sponsio.runner.SponsioRunner    : $230.5222241000507 on Belgia to draw on Boookie Betano
2022-11-23 15:51:08.276  INFO 22912 --- [           main] eridanus.sponsio.runner.SponsioRunner    : $607.7404089910427 on Belgia to win on Boookie Betano
2022-11-23 15:51:08.276  INFO 22912 --- [           main] eridanus.sponsio.runner.SponsioRunner    : $161.73736690890652 on Canada to win on Boookie Betano
2022-11-23 15:51:08.276  INFO 22912 --- [           main] eridanus.sponsio.runner.SponsioRunner    : Guaranteed profit: 2.7716748352204377

2022-11-23 15:51:08.278  INFO 22912 --- [           main] eridanus.sponsio.runner.SponsioRunner    : Arbitrage opportunity found!
2022-11-23 15:51:08.278  INFO 22912 --- [           main] eridanus.sponsio.runner.SponsioRunner    : Levadiakos vs Aris Salonic
2022-11-23 15:51:08.278  INFO 22912 --- [           main] eridanus.sponsio.runner.SponsioRunner    : Bet: 
2022-11-23 15:51:08.278  INFO 22912 --- [           main] eridanus.sponsio.runner.SponsioRunner    : $570.0718314612158 on Aris Salonic to win on Boookie Betano
2022-11-23 15:51:08.278  INFO 22912 --- [           main] eridanus.sponsio.runner.SponsioRunner    : $241.28621703707276 on Aris Salonic to draw on Boookie Betano
2022-11-23 15:51:08.278  INFO 22912 --- [           main] eridanus.sponsio.runner.SponsioRunner    : $188.64195150171142 on Levadiakos to win on Boookie Betano
2022-11-23 15:51:08.278  INFO 22912 --- [           main] eridanus.sponsio.runner.SponsioRunner    : Guaranteed profit: 37.53073325941273
```


### Tennis

```
2022-11-23 16:13:58.228  INFO 1904 --- [           main] eridanus.sponsio.runner.SponsioRunner    : Arbitrage found for match: Nuudi M. vs Schoofs B.
2022-11-23 16:13:58.228  INFO 1904 --- [           main] eridanus.sponsio.runner.SponsioRunner    : Odds from BETANO: 1.21, Odds from BETANO: 9.5
2022-11-23 16:13:58.229  INFO 1904 --- [           main] eridanus.sponsio.runner.SponsioRunner    : Bet 826.4462809917355 on 1.21 BETANO
2022-11-23 16:13:58.229  INFO 1904 --- [           main] eridanus.sponsio.runner.SponsioRunner    : Bet 105.26315789473684 on 9.5 BETANO
2022-11-23 16:13:58.229  INFO 1904 --- [           main] eridanus.sponsio.runner.SponsioRunner    : Winning: 68.29056111352759
2022-11-23 16:13:58.229  INFO 1904 --- [           main] eridanus.sponsio.runner.SponsioRunner    : Odds from BETANO: 2.95, Odds fr
```