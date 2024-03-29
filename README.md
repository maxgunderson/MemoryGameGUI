# GUI Memory Game 

This project is a tile-based memory game developed using the Java Swing library. 

## Overview

Players uncover pairs of tiles to match them. The GUI interface displays the game board within a main window. Players click to reveal tiles, aiming to have every tile uncovered. There are varying difficulty levels, starting at "Medium" by default. The game keeps track of the current board version and turn count, providing real-time feedback on whether the clicked tiles were a match. Upon incorrect guesses, tiles briefly display before hiding again, incrementing the turn count. Successful matches remain visible and incorrect matches return to being covered. Once all tiles are matched, a new window prompts players with options to either start again or exit the game. Additionally, players can access instructions and view the leaderboard.

## Leaderboard 
If the players turn count is less than that of the current leaderboards, the user has the option to input their name and the leaderboard is updated and displayed.

## Character Sets
The game offers two character sets to choose from: the English alphabet and optionally, the Hebrew alphabet. These sets are statically generated using unicode values. 
