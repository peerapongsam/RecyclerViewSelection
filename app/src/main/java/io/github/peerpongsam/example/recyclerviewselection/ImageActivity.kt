package io.github.peerpongsam.example.recyclerviewselection

import android.os.Bundle
import android.view.MotionEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StableIdKeyProvider
import androidx.recyclerview.selection.StorageStrategy
import kotlinx.android.synthetic.main.activity_image.*

class ImageActivity : AppCompatActivity() {

    lateinit var selectionTracker: SelectionTracker<Long>

    private val itemDetailsLookup = object : ItemDetailsLookup<Long>() {
        override fun getItemDetails(e: MotionEvent): ItemDetails<Long>? {
            return recyclerView.findChildViewUnder(e.x, e.y)?.let { view ->
                (recyclerView.getChildViewHolder(view) as? ImageViewHolder)?.let { holder ->
                    return object : ItemDetails<Long>() {
                        override fun getSelectionKey() = holder.itemId
                        override fun getPosition() = holder.adapterPosition
                    }
                }
            }
        }
    }

    private val selectionPredicate = object : SelectionTracker.SelectionPredicate<Long>() {
        override fun canSelectMultiple(): Boolean {
            return true
        }

        override fun canSetStateForKey(key: Long, nextState: Boolean): Boolean {
            return if (selectionTracker.selection.size() >= 5) {
                Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show()
                false
            } else {
                true
            }
        }

        override fun canSetStateAtPosition(position: Int, nextState: Boolean): Boolean {
            return true
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)

        selectionTracker = SelectionTracker.Builder<Long>(
            "image-selection-id",
            recyclerView,
            StableIdKeyProvider(recyclerView),
            itemDetailsLookup,
            StorageStrategy.createLongStorage()
        )
            .withSelectionPredicate(selectionPredicate)
            .build()

        if (savedInstanceState != null) {
            selectionTracker.onRestoreInstanceState(savedInstanceState)
        }

        val imageAdapter = ImageAdapter(selectionTracker)
        recyclerView.apply { adapter = imageAdapter }
        selectionTracker.addObserver(object : SelectionTracker.SelectionObserver<Long>() {
            override fun onSelectionChanged() {
                super.onSelectionChanged()
                title = if (selectionTracker.hasSelection()) {
                    getString(R.string.selection, selectionTracker.selection.size(), 5)
                } else {
                    getString(R.string.app_name)
                }
            }
        })

        val imageRepository = ImageRepository()
        imageAdapter.submitList(imageRepository.getImage())
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        selectionTracker.onSaveInstanceState(outState)
    }
}
