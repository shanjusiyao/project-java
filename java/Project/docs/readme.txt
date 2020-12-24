Readme
sunj10
zhengy7
Final Project: Gomuku

(user manual that describes is in manual file with the video)

At the beginning of the program, user will see a option Dialog, which there are two options, one is play solo, which means user will play with AI
For AI part.
First of all, stand-alone mode is divided into three kinds of difficulty, and the selection logic made by AI will be different. The judgment made by AI is mainly based on the following aspects.
A game tree is a graphical representation of a dynamic game in which the actions of the players are sequentially arranged into a tree(from wikipedia game tree).
As the name implies, a game tree is a graphical representation of the decision actions taken by the players in turn at the same number of times. For any kind of game competition, we can build it into a game tree whose nodes correspond to a certain chess game, whose branches represent one-step decision making, whose roots represent the starting position, and whose leaves represent the end of the game.

With the concept of a game tree, what we need to do is search this huge game tree. Since each branch of the game tree is a one-step decision, we need to conduct a depth-first search, and we plan to use the minimax search algorithm. Rank each branch of the game tree (if there are too many branches, multi-threading will be used and processed in different threads)
(preliminary design below) :
When there are five pieces linked together: at this point, one side has won, we set the score to be infinite;
When there are two pieces connected (both appear at the same time, and none of them are blocked) : at this point, I think this side has a great chance to win the game, we set the score to 10000;
When there are four pieces connected (and not blocked) : At this point, I think this side has a good chance to win the game, we set the score to 2000;
When there are two or three pieces connected (both appear at the same time, and none of them are blocked) : At this time, I think this side has an advantage, so we set the score to 1000; When there are three pieces connected (and not blocked) : At this point I think this side has a chance to win, we set the score to 500;
When there are two pieces connected (both appear at the same time, and neither is blocked) : then we set the score to 100;
When there are two pieces connected (and not blocked) : we set the score to 50;
When only one single piece appears: we set the score to 10;


another option is to play with real people.

If user choose to click play with real person, then it will show up the Internet connect section part, user can choose waitting for another user or find the other player.
For finding another user, there are default values for user to input, user can change it as they want. User also can choose to cancel the Internet connect, they can set it later.

For the whole frame, there is a menu bar in the top of the application, the first one is to set the parameter butter. the first parameter is username, the second one is the change the order.
if user is the host, it will default be the first one. if both player decide to change order, both application need to click change order. so that the application will not go wrong.
The last one is the Internet connect part, it will have the same effect, so if user has already success connected, they do not need to set it again.

The dispose menu is to hide the config part, so that user can focus on the main part which is the chess.

When user want to start the game, both player need to click ready to tell opponent that they are ready for the chess. Both players are ready for the game, the message will show up the information, and the first one player can click the start and then click the place player want to place their chess.
After that user need to click receive button to wait the opponent to go. after the receive the information on the Board, user can continue choose the place on the board, (without click Start button) and then click receive button repeatly.
which means:
Start ---  place chess --- Receive --- place chess --- Receive ........ until end or surrender

The second player need to click Receive button first and then click Start button, then go the same process as the first player
which means:
Receive --- Start ---  place chess --- Receive --- place chess ........ until end or surrender
