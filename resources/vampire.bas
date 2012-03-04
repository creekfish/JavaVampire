100 KEY OFF: COLOR 10, 1: CLS : WIDTH 80
110 PRINT , "Welcome to the VAMPIRE'S CASTLE Adventure": PRINT : PRINT
112 INPUT "Do you need the instructions"; A$: A$ = LEFT$(A$ + " ", 1)
114 IF A$ = "y" OR A$ = "Y" THEN GOSUB 2000:  ELSE PRINT
120 DIM D$(19), O$(31), L(25), P(19, 6): L = 1
130 FOR x = 1 TO 19: READ D$(x): NEXT x
140 DATA Entrance Hall,Study,Library,Armory,Tower
150 DATA Lower Tower,Chapel,Brick Fireplace
160 DATA Hidden Corridor,Secret Passage
170 DATA Underground Lake Chamber,Boat,Alchemist's Lab
180 DATA Storeroom,Overhang,Gallery,Antechamber,Vampire's Tomb
190 DATA Torture Chamber
200 FOR x = 1 TO 31: READ O$(x): IF x > 6 THEN READ L(x - 6)
220 NEXT: T2 = 8: R = 11: WS$ = "Wooden Stakes"
230 DATA North,South,East,West,"Up ",Down,Sledge Hammer,5
240 DATA Timepiece,1,Coil of Rope,9,Parchment Scroll,3,Axe,4,Oar,6,Key,99
250 DATA Holywater,7,Flask of Oil,13,Crate,14,Bucket,14
260 DATA Torch,8,Nails,15,Tapestry,16,Boat,11,Rusty Door,17
270 DATA Closed Coffin,18,Fire in the Fireplace,2
280 DATA Bookcase,3,Sign,1,Parapets,5,Brick Fireplace,2
290 DATA Rat,19,Wine,2,Cheddar Cheese,2
300 FOR Y = 1 TO 19: FOR x = 1 TO 6: READ P(Y, x): NEXT x, Y
310 DATA ,,3,2,,,,,1,,,,,,4,1,,,,,5,3,,,,,,4,,,,7,,,5,,6,,,,4,
320 DATA ,2,,,,,13,,,,3,,11,8,,19,,,,10,,,,,,11,,,,,14,9,,,,
330 DATA ,13,,,2,,,,,,,16,,,,,,,,16,,,,,,17,,,,,,,10,13,,
340 W$ = "GO GETLOODROHITOPETIEPUSKILOILROWCLITAKREATHRBRE"
350 N$ = "N  01S  02E  03W  04U  05D  06HAM07SCR10AX 11WAT14OIL15DOO22COF23VAM23FIR28"
355 O$ = "NORSOUEASWESUP DOWSLETIMROPPARAXEOARKEYHOLFLACRABUCTORNAITAPBOARUSCLOFIRBOOSIGPARBRIRATWINCHE"
360 COLOR 2, 0: PRINT "You are in the "; D$(L): Y = 0: L2 = L
370 IF L(12) = 0 OR L < 10 OR (L > 12 AND L <> 18) THEN 390
380 PRINT "It's Dark! you can't see": GOTO 440
390 PRINT "You see "; : FOR x = 1 TO 25: IF L(x) = L2 THEN PRINT O$(x + 6); ", "; : Y = Y + 1
400 NEXT: IF Y = 0 THEN PRINT "nothing interesting." ELSE PRINT CHR$(29); CHR$(29); " "
420 PRINT "Obvious exits are: "; : FOR x = 1 TO 6: IF P(L, x) > 0 THEN PRINT O$(x); " ";
430 NEXT: PRINT
440 S = 0: F = 0: COLOR 10, 0: PRINT : INPUT "What do you want to do"; A$: PRINT : COLOR 2
450 A$ = A$: B$ = "   ": FOR x = 1 TO LEN(A$)
460 IF MID$(A$, x, 1) = " " THEN B$ = MID$(A$ + "   ", x + 1, 3)
490 NEXT: IF LEN(A$) = 1 THEN B$ = A$ + "  ": A$ = "GO "
500 GOSUB 1800: L2 = L: T1 = T1 + 1: IF T1 = 60 THEN T1 = 0: T2 = T2 + 1
510 IF T2 = 12 THEN COLOR 7, 0: PRINT " It's midnight: the Vampire is awake, He's at your neck "; : GOTO 1750
520 IF A$ = "SWI" AND L = 11 THEN COLOR 7, 0: PRINT " You have Drowned in the ice cold water "; : GOTO 1750
530 x = INSTR(N$, B$): IF x MOD 5 = 1 THEN S = VAL(MID$(N$, x + 3, 2)): GOTO 590
560 x = INSTR(O$, B$): IF x MOD 3 = 1 THEN S = (x + 2) / 3
590 x = INSTR(W$, A$): IF x MOD 3 = 1 THEN F = (x + 2) / 3
620 IF F > 11 THEN F = F - 11
630 IF A$ <> "INV" THEN 680
640 PRINT "You are carrying: "; : A = 0: FOR x = 1 TO 25
650 IF L(x) = 0 THEN PRINT O$(x + 6); ", "; : A = A + 1
660 NEXT: IF A = 0 THEN PRINT "nothing";
670 PRINT : GOTO 440
680 ON F GOTO 720, 890, 1100, 1240, 1300, 1390, 1480, 1560, 1610, 1680, 1720
690 PRINT "I don't know how to do that": GOTO 440
700 PRINT "I don't know that word": GOTO 440
710 REM  -GO-
720 IF S < 1 OR S > 6 THEN 760
730 IF P(L, S) > 0 THEN L = P(L, S): GOTO 360
740 PRINT "You can't go that way": GOTO 440
750 PRINT "You see nothing special": GOTO 440
760 IF S <> 28 OR L <> 2 THEN 790
770 IF FI = 0 THEN COLOR 0, 7: PRINT " You have Burned to Death "; : GOTO 1750
780 L = 8: GOTO 360
790 IF S <> 27 OR L <> 5 THEN 820
800 IF LEFT$(O$(27), 1) = "R" THEN L = 6: PRINT "Climbed down rope": PRINT : GOTO 360
810 COLOR 0, 7: PRINT " You fell and Died "; : GOTO 1750
820 IF S = 21 AND L(15) = L THEN L = 12: GOTO 360
830 IF L = 16 AND B$ = "OVE" THEN 860
840 IF S < 1 THEN 700
850 PRINT "You can't go there": GOTO 440
860 IF L(10) = L AND O$(16) = "Crate" THEN L = 15: GOTO 360
870 PRINT "It's a little too high": GOTO 440
880 REM  -GET-
890 IF S < 7 THEN GOTO 700
900 IF C > 6 THEN PRINT "You can't carry any more": GOTO 440
910 IF L(S - 6) <> L THEN 1070
920 IF S <> 14 THEN 960
940 V$ = "In": GOSUB 1850: IF A$ = "BUC" AND L(11) = 0 THEN 1080
950 PRINT "You can't do that": GOTO 440
960 IF S <> 20 THEN 990
970 IF TA = 0 THEN PRINT "It's nailed to an overhang": GOTO 440
980 P(16, 1) = 17: PRINT "AHA! - A hole in the wall": PRINT : GOTO 1080
990 IF S <> 19 THEN 1020
1000 IF L(1) <> 0 THEN PRINT "You have no hammer": GOTO 440
1010 IF L(13) = 15 THEN TA = 1: PRINT "The tapestry is loose": GOTO 1080
1020 IF S <> 13 THEN 1050
1030 L(23) = L: IF L(25) = L THEN 1080
1040 PRINT "The Rat has it": GOTO 440
1050 IF S > 20 AND S < 30 THEN PRINT "You can't get it": GOTO 440 ELSE 1080
1070 PRINT "I don't see any "; O$(S): GOTO 440
1080 C = C + 1: L(S - 6) = 0: PRINT "You got the "; O$(S): GOTO 440
1090 REM  -LOOK-
1100 IF S < 7 THEN 360
1120 IF S = 26 AND L = 1 THEN PRINT "'The Vampire Wakes at Midnight'": GOTO 440
1130 IF S <> 29 THEN 1160
1140 IF L(23) <> L THEN 1070
1150 PRINT "A Key is in it's mouth!": L(7) = L: GOTO 440
1160 IF S <> 10 THEN 1200
1170 IF L(4) = 0 THEN 1190
1180 PRINT "You don't have it": GOTO 440
1190 PRINT "The Scroll reads: 'Not all exits are obvious.'": GOTO 440
1200 IF S <> 8 THEN 750
1210 IF L(2) <> 0 THEN 1180
1220 PRINT USING "The time is now ##:##"; T2; T1: GOTO 440
1230 REM  -DROP-
1240 IF S < 7 THEN GOTO 700
1250 IF L(S - 6) <> 0 THEN 1180
1260 IF S <> 14 OR L <> 2 THEN 1280
1270 O$(24) = "Smoldering Ashes": FI = 1: L(8) = 99: C = C - 1: GOTO 360
1280 PRINT "Okay, the "; O$(S); " is on the "; D$(L); " floor": L(S - 6) = L: C = C - 1: GOTO 440
1290 REM  -HIT-
1300 IF S < 7 THEN 700
1310 IF L(S - 6) <> L AND (L <> 8 OR S <> 28) THEN 1070
1330 V$ = "With": GOSUB 1850: IF A$ <> "AXE" OR S <> 16 OR L(5) <> 0 THEN 1350
1340 O$(16) = WS$: N$ = N$ + "WOO16STA16": GOTO 360
1350 IF (A$ <> "SLE" AND A$ <> "HAM") OR S <> 28 OR L(1) <> 0 THEN 1370
1360 O$(28) = "Broken Fireplace": P(8, 1) = 10: D$(8) = O$(28): GOTO 360
1370 PRINT "Nothing happened": GOTO 440
1380 REM  -OPEN-
1390 IF S < 7 THEN 700
1400 IF L(S - 6) <> L THEN 1070
1410 IF S <> 23 THEN 1440
1420 IF L(7) <> 0 THEN PRINT "The coffin is locked ... "; : GOTO 1370
1430 O$(23) = "Vampire in the Coffin": GOTO 360
1440 IF S <> 22 THEN 1370
1450 IF OI = 0 THEN PRINT "Too much rust ... "; : GOTO 1370
1460 O$(22) = "Open Door": P(17, 1) = 18: GOTO 360
1470 REM  -TIE-
1480 IF S < 7 THEN 700
1490 IF L(S - 6) <> 0 THEN 1180
1500 IF S <> 9 THEN 950
1520 V$ = "To": GOSUB 1850: IF A$ <> "PAR" THEN 950
1530 L(3) = 99: C = C - 1: O$(27) = "Rope tied to the Parapet": N$ = N$ + "ROP27"
1540 PRINT O$(27); "!": GOTO 440
1550 REM  -PUSH-
1560 IF S < 7 THEN 700
1570 IF L(S - 6) <> L THEN 1070
1580 IF S <> 25 THEN 1370
1590 PRINT "Aha! - You have revealed a Doorway": PRINT : P(L, 6) = 9: GOTO 360
1600 REM  -KILL-
1610 IF S < 7 THEN 700
1620 IF S <> 23 OR LEFT$(O$(23), 1) <> "V" THEN 950
1630 V$ = "With": GOSUB 1850
1640 IF (A$ = "WOO" OR A$ = "STA") AND L(10) = 0 AND O$(16) = WS$ THEN 1660
1650 COLOR 0, 7: PRINT " You Failed!  The Vampire awakes and sucks your Blood! "; : GOTO 1750
1660 COLOR 26: PRINT "Congratulations!  You have killed the Vampire": GOTO 1750
1670 REM  -OIL-
1680 IF L(9) <> 0 OR L <> 17 OR S <> 22 THEN 950
1690 PRINT "The Door squeaks Open": PRINT
1700 OI = 1: O$(22) = "Open Door": P(17, 1) = 18: GOTO 360
1710 REM  -ROW-
1720 IF L <> 12 OR L(6) <> 0 THEN 950
1730 R = 27 - R: L = R: PRINT "You have rowed to the "; D$(L)
1740 PRINT : L(15) = L: GOTO 360
1750 COLOR 10, 0: PRINT : PRINT : INPUT "Would you like to try again"; A$: GOSUB 1800
1760 IF LEFT$(A$, 1) = "Y" THEN RUN 100
1770 IF LEFT$(A$, 1) = "R" THEN T1 = T1 - 2: GOTO 440
1780 KEY ON: END
1800 REM  - MAKE INPUT U/C -
1810 A$ = LEFT$(A$ + "   ", 3): FOR I = 1 TO 3
1820 CH = ASC(MID$(A$, I, 1)): IF CH > 96 THEN MID$(A$, I, 1) = CHR$(CH - 32)
1830 CH = ASC(MID$(B$, I, 1)): IF CH > 96 THEN MID$(B$, I, 1) = CHR$(CH - 32)
1840 NEXT: RETURN
1850 REM - GET "WHAT" -
1860 COLOR 10: PRINT TAB(40); CHR$(30); CHR$(30); : PRINT "-- "; V$; " what"; : INPUT A$
1870 COLOR 2: PRINT : GOSUB 1800: RETURN
2000 PRINT : PRINT : COLOR 2
2010 PRINT "VAMPIRE'S CASTLE has a concealed goal.  You learn what the goal is"
2020 PRINT "by exploring your surroundings.  The computer will act as your eyes"
2030 PRINT "and hands.  It will accepts short phrases as commands and assumes"
2040 PRINT "that the first word is a verb and the last word is the object."
2050 PRINT "For example: READ THE SIGN.  The computer has a vocabulary of about"
2060 PRINT "70 words.  Some of the more important words you should know before"
2070 PRINT "you start �laying are: GET <object>, DROP <object>, LOOK <object>"
2080 PRINT "or just LOOK, GO <direction> or <place>, and INVENTORY (tells what"
2084 PRINT "you are carrying).  The computer knows the abbreviations: E, W, N,"
2086 PRINT "S, U and D for GO EAST, GO WEST, etc.": PRINT
2090 PRINT "The computer's vocabulary is good, but limited.  If you are having"
2100 PRINT "trouble doing something, try re-phrasing the command or you may"
2110 PRINT "need some object to accomplish the task.  By the way, the computer"
2120 PRINT "only looks at the first 3 letters of each word.": PRINT
2130 RETURN

