=== 中间代码 (TAC) ===
  1: func_main1:
  2: t1 = &p1.x
  3: STORE
  4: t2 = &p1.y
  5: STORE
  6: for_init2:
  7: i = alloc 4
  8: i = 0
  9: for_condition3:
 10: if count == 0 goto for_end6
 11: for_body4:
 12: t3 = &matrix[i]
 13: LOAD
 14: t5 = &t4[i]
 15: STORE
 16: t6 = sum + null
 17: sum = t6
 18: for_update5:
 19: t7 = i
 20: t8 = i + 1
 21: i = t8
 22: goto for_condition3
 23: for_end6:
 24: while7:
 25: if 0 == 0 goto while_end9
 26: while_body8:
 27: if 0 == 0 goto else10
 28: t9 = count
 29: t10 = count - 1
 30: count = t10
 31: goto endif11
 32: else10:
 33: endif11:
 34: if 5 == 0 goto else12
 35: goto endif13
 36: else12:
 37: endif13:
 38: t11 = count - 1
 39: count = t11
 40: goto while7
 41: while_end9:
 42: return 0
