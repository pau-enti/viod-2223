package com.example.firstapp.dragging

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.DragEvent
import android.view.View
import androidx.core.view.children
import com.example.firstapp.databinding.ActivityDragAndDropBinding
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlin.reflect.safeCast

class DragAndDropActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDragAndDropBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDragAndDropBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.target1.children.forEach { child ->
            child.setOnLongClickListener {
//            it.visibility = View.GONE
                val shadow = View.DragShadowBuilder(it)
                it.startDragAndDrop(null, shadow, it, 0)
            }
        }

        binding.target1.setOnDragListener(dragListener)
        binding.target2.setOnDragListener(dragListener)

        binding.target1.setBackgroundColor(Color.CYAN)
        binding.target2.setBackgroundColor(Color.CYAN)
    }

    private val dragListener = View.OnDragListener { destinationView, draggingData ->
        val event = draggingData.action
        val objecteMobil = draggingData.localState // És el tercer parametre

        when (event) {
            DragEvent.ACTION_DRAG_STARTED -> destinationView.setBackgroundColor(Color.MAGENTA)
            DragEvent.ACTION_DRAG_ENTERED -> destinationView.setBackgroundColor(Color.YELLOW)
            DragEvent.ACTION_DRAG_EXITED ->  destinationView.setBackgroundColor(Color.MAGENTA)
            DragEvent.ACTION_DRAG_ENDED -> destinationView.setBackgroundColor(Color.CYAN)
            DragEvent.ACTION_DROP -> {
                if (objecteMobil !is Chip)
                    return@OnDragListener true

                ChipGroup::class.safeCast(objecteMobil.parent)?.removeView(objecteMobil)
                ChipGroup::class.safeCast(destinationView)?.addView(objecteMobil)
            }
        }


        true
    }
}