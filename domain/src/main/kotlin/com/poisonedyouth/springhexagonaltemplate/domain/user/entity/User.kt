package com.poisonedyouth.springhexagonaltemplate.domain.user.entity

import com.poisonedyouth.springhexagonaltemplate.common.vo.Identity
import com.poisonedyouth.springhexagonaltemplate.domain.user.vo.Address
import com.poisonedyouth.springhexagonaltemplate.domain.user.vo.Name

data class User(val identity: Identity, val name: Name, val address: Address)
