package com.mustfaibra.qpaymultiplatform.utils.permissions

sealed class Permission(val name: String) {
	data object Camera : Permission(name = "Camera")
}
