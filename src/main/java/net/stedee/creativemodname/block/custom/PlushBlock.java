package net.stedee.creativemodname.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;
import net.stedee.creativemodname.sound.ModdedSounds;

import java.util.function.Supplier;

public class PlushBlock extends HorizontalFacingBlock {
    public static final MapCodec<PlushBlock> CODEC = createCodec(PlushBlock::new);
    private static VoxelShape PLUSHIE_SHAPE;
    public static final BooleanProperty LIT = Properties.LIT;

    public PlushBlock(Settings settings) {
        super(settings);
    }

    public PlushBlock(Settings settings, Supplier<VoxelShape> shape) {
        super(settings);
        PLUSHIE_SHAPE = shape.get();
    }

    @Override
    protected MapCodec<? extends HorizontalFacingBlock> getCodec() {
        return CODEC;
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return PLUSHIE_SHAPE;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, LIT);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing()).with(LIT, Boolean.FALSE);
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return PLUSHIE_SHAPE;
    }

    @Override
    protected VoxelShape getCameraCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return PLUSHIE_SHAPE;
    }

    @Override
    protected VoxelShape getRaycastShape(BlockState state, BlockView world, BlockPos pos) {
        return PLUSHIE_SHAPE;
    }

    @Override
    protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return Block.sideCoversSmallSquare(world, pos.down(), Direction.UP);
    }

    @Override
    protected BlockState getStateForNeighborUpdate(
            BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos
    ) {
        return direction == Direction.DOWN && !this.canPlaceAt(state, world, pos)
                ? Blocks.AIR.getDefaultState()
                : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (stack.isOf(Items.GLOW_INK_SAC) && state.get(LIT).equals(Boolean.FALSE)) {
            world.playSound(player, pos, SoundEvents.ITEM_GLOW_INK_SAC_USE, SoundCategory.BLOCKS, 1.0F, world.getRandom().nextFloat() * 0.4F + 0.8F);
            world.setBlockState(pos, state.with(Properties.LIT, Boolean.TRUE), Block.NOTIFY_ALL_AND_REDRAW);
            world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
            if (player != null) {
                stack.decrement(1);
            }

            return ItemActionResult.success(world.isClient());
        }
        if (!player.getAbilities().allowModifyWorld) {
            return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        } else {
            world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), ModdedSounds.PLUSHIE_SQUEAKS, SoundCategory.BLOCKS, 1f, 1f);
            return ItemActionResult.SUCCESS;
        }
    }

    @Override
    protected float calcBlockBreakingDelta(BlockState state, PlayerEntity player, BlockView world, BlockPos pos) {
        float f = state.getHardness(world, pos);
        if (f == -1.0F) {
            return 0.0F;
        } else {
            int i = player.canHarvest(state) ? 30 : 100;
            if (player instanceof PlayerEntity && player.getEquippedStack(EquipmentSlot.MAINHAND).isOf(Items.SHEARS)) {
                i = i - 29;
            }
            return player.getBlockBreakingSpeed(state) / f / (float)i;
        }
    }
}
