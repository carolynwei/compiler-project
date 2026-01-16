=== 中间代码 (TAC) ===
  1: func_main1:
  2: t2 = a + 1
  3: t3 = t2 + 1
  4: t5 = b - 1
  5: t6 = t5 - 1
  6: t7 = t3 + t6
  7: t8 = t7 - c
  8: t9 = t8 * 2
  9: t10 = t9 / 2
 10: t11 = t10 % 3
 11: a = t11
 12: if t6 != 0 goto true2
 13: goto end3
 14: true2:
 15: t12 = a
 16: end3:
 17: return t12
