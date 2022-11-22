# Sponsio

Sponsio, latin for bet, is a software tool used to scan Romanian bookies in order to find arbitrage betting opportunities.

## Arbitrage betting

Arbitrage betting is a form of betting that guarantees profit to the player no matter the outcome of a match.

This can happen due to bookies miss-aligning their odds creating an opportunity for the player to bet on different bookies
on the same game (on different outcomes) and coming out ahead no matter what the result is.

For this tool I'm using only 2-way arbitrage meaning there are some restrictions:
 
Matches need to have only two possible results. Certain games such as football can have 3 results (either team wins or it's a draw), so they will not be included. 
Sports like tennis are great for this since there's always one player winning, meaning we can apply 2-way arbitrage here

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

## Math Formula

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

