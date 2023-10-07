package com.mustfaibra.qpaymultiplatform.resources

import android.content.Context
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.Resource
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.format

actual class StringsProvider(
	private val context: Context,
) {
	actual fun get(
		stringId: StringResource,
		args: List<Any>
	): String {
		return if (args.isEmpty()) {
			StringDesc.Resource(stringId).toString(context)
		} else {
			stringId.format(*args.toTypedArray()).toString(context)
		}
	}
}
