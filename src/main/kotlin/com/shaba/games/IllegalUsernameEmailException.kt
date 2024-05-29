package com.shaba.games

class IllegalUsernameEmailException(cause: Exception) : RuntimeException("Cannot use that username or email", cause)