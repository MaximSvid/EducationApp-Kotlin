package com.example.educationappmaximsvidrak.customElements

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager

class NonScrollableLinearLayoutManager (context: Context) : LinearLayoutManager (context) {
    override fun canScrollVertically(): Boolean {
        return false
    }

    override fun canScrollHorizontally(): Boolean {
        return false
    }
}