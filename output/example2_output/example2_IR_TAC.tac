=== 中间代码 (TAC) ===
  1: func_add1:
  2: return b
  3: func_multiply2:
  4: return b
  5: func_factorial3:
  6: if 1 == 0 goto else4
  7: return 1
  8: goto endif5
  9: else4:
 10: arg 1
 11: t1 = call factorial
 12: return t1
 13: endif5:
 14: func_main6:
 15: for_init7:
 16: i = alloc 4
 17: i = 0
 18: for_condition8:
 19: if 10 == 0 goto for_end11
 20: for_body9:
 21: t2 = &numbers[i]
 22: STORE
 23: for_update10:
 24: t3 = i
 25: t4 = i + 1
 26: i = t4
 27: goto for_condition8
 28: for_end11:
 29: arg 5
 30: arg 3
 31: t5 = call add
 32: result = t5
 33: arg result
 34: arg 2
 35: t6 = call multiply
 36: result = t6
 37: arg 5
 38: t7 = call factorial
 39: result = t7
 40: for_init12:
 41: i = alloc 4
 42: i = 0
 43: for_condition13:
 44: if 10 == 0 goto for_end16
 45: for_body14:
 46: t8 = &numbers[i]
 47: LOAD
 48: t10 = sum + t9
 49: sum = t10
 50: for_update15:
 51: t11 = i
 52: t12 = i + 1
 53: i = t12
 54: goto for_condition13
 55: for_end16:
 56: return sum
