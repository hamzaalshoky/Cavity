package net.itshamza.cavity.block.menu;

import net.itshamza.cavity.block.ModBlocks;
import net.itshamza.cavity.block.entity.CavityBlockEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

public class CavityBlockMenu extends AbstractContainerMenu {
    private final CavityBlockEntity blockEntity;
    private final ContainerLevelAccess containerAccess;

    private final Slot itemSlot;
    public CavityBlockMenu(int pContainerId, Inventory inv, FriendlyByteBuf extraData) {
        this(pContainerId, inv, (CavityBlockEntity) inv.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(1));
    }

    public CavityBlockMenu(int pContainerId, Inventory playerInventory, CavityBlockEntity blockEntity, ContainerData data) {
        super(ModMenuTypes.CAVITY_BLOCK_MENU.get(), pContainerId);
        this.blockEntity = blockEntity;
        this.containerAccess = ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos());

        // Slot for storing the item in the Sewing Block
        this.itemSlot = addSlot(new Slot((Container) blockEntity, 0, 80, 35));

        // Add player's inventory slots
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for (int i = 0; i < 9; i++) {
            addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(containerAccess, player, ModBlocks.CHEST_CAVITY.get());
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index < 1) {
                if (!this.moveItemStackTo(itemstack1, 1, 37, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, 1, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }
        if (!this.blockEntity.getStoredItem().isEmpty()) {
            this.blockEntity.setStoredItem(itemSlot.getItem());
        }
        return itemstack;
    }
}