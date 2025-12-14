=== 中间代码 (TAC) ===
  1: func_findMax1:
  2: for_init2:
  3: i = alloc 4
  4: i = 1
  5: for_condition3:
  6: if size == 0 goto for_end6
  7: for_body4:
  8: if max == 0 goto else7
  9: t1 = &arr[i]
 10: LOAD
 11: max = t2
 12: goto endif8
 13: else7:
 14: endif8:
 15: for_update5:
 16: t3 = i
 17: t4 = i + 1
 18: i = t4
 19: goto for_condition3
 20: for_end6:
 21: return max
 22: func_main9:
 23: t5 = &scores[0]
 24: STORE
 25: t6 = &scores[1]
 26: STORE
 27: t7 = &scores[2]
 28: STORE
 29: t8 = &scores[3]
 30: STORE
 31: t9 = &scores[4]
 32: STORE
 33: for_init10:
 34: i = alloc 4
 35: i = 0
 36: for_condition11:
 37: if 5 == 0 goto for_end14
 38: for_body12:
 39: t10 = &students[i]
 40: LOAD
 41: t12 = &t11.id
 42: STORE
 43: t13 = &students[i]
 44: LOAD
 45: t15 = &t14.age
 46: STORE
 47: t16 = &scores[i]
 48: LOAD
 49: t18 = &students[i]
 50: LOAD
 51: t20 = &t19.grade
 52: STORE
 53: for_update13:
 54: t21 = i
 55: t22 = i + 1
 56: i = t22
 57: goto for_condition11
 58: for_end14:
 59: for_init15:
 60: i = alloc 4
 61: i = 0
 62: for_condition16:
 63: if 5 == 0 goto for_end19
 64: for_body17:
 65: for_init20:
 66: j = alloc 4
 67: j = 1
 68: for_condition21:
 69: if 5 == 0 goto for_end24
 70: for_body22:
 71: t23 = &students[j]
 72: LOAD
 73: t25 = &t24.grade
 74: LOAD
 75: if t26 == 0 goto else25
 76: t27 = &students[j]
 77: LOAD
 78: t29 = &students[i]
 79: STORE
 80: t30 = &students[j]
 81: STORE
 82: goto endif26
 83: else25:
 84: endif26:
 85: for_update23:
 86: t31 = j
 87: t32 = j + 1
 88: j = t32
 89: goto for_condition21
 90: for_end24:
 91: for_update18:
 92: t33 = i
 93: t34 = i + 1
 94: i = t34
 95: goto for_condition16
 96: for_end19:
