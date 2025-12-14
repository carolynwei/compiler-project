=== 中间代码 (TAC) ===
  1: func_main1:
  2: result = c
  3: result = c
  4: result = c
  5: result = b
  6: t1 = a
  7: t2 = a + 1
  8: a = t2
  9: t3 = a + 1
 10: a = t3
 11: t4 = b
 12: t5 = b - 1
 13: b = t5
 14: t6 = b - 1
 15: b = t6
 16: t7 = a + b
 17: a = t7
 18: t8 = a - c
 19: a = t8
 20: t9 = a * 2
 21: a = t9
 22: t10 = a / 2
 23: a = t10
 24: t11 = a % 3
 25: a = t11
 26: if b != 0 goto true2
 27: t12 = b
 28: goto end3
 29: true2:
 30: t12 = a
 31: end3:
 32: result = t12
 33: return result
