package com.rescue.offlineapp.data;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@SuppressWarnings({"unchecked", "deprecation"})
public final class AlertDao_Impl implements AlertDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<AlertEntity> __insertionAdapterOfAlertEntity;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<AlertEntity> __deletionAdapterOfAlertEntity;

  private final EntityDeletionOrUpdateAdapter<AlertEntity> __updateAdapterOfAlertEntity;

  private final SharedSQLiteStatement __preparedStmtOfMarkAsRead;

  private final SharedSQLiteStatement __preparedStmtOfDeleteOldAlerts;

  public AlertDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfAlertEntity = new EntityInsertionAdapter<AlertEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `alerts` (`id`,`priority`,`need`,`latitude`,`longitude`,`timestamp`,`victimId`,`message`,`isRead`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final AlertEntity entity) {
        statement.bindLong(1, entity.getId());
        final String _tmp = __converters.fromPriority(entity.getPriority());
        if (_tmp == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, _tmp);
        }
        if (entity.getNeed() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getNeed());
        }
        statement.bindDouble(4, entity.getLatitude());
        statement.bindDouble(5, entity.getLongitude());
        statement.bindLong(6, entity.getTimestamp());
        if (entity.getVictimId() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getVictimId());
        }
        if (entity.getMessage() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getMessage());
        }
        final int _tmp_1 = entity.isRead() ? 1 : 0;
        statement.bindLong(9, _tmp_1);
      }
    };
    this.__deletionAdapterOfAlertEntity = new EntityDeletionOrUpdateAdapter<AlertEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `alerts` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final AlertEntity entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfAlertEntity = new EntityDeletionOrUpdateAdapter<AlertEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `alerts` SET `id` = ?,`priority` = ?,`need` = ?,`latitude` = ?,`longitude` = ?,`timestamp` = ?,`victimId` = ?,`message` = ?,`isRead` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final AlertEntity entity) {
        statement.bindLong(1, entity.getId());
        final String _tmp = __converters.fromPriority(entity.getPriority());
        if (_tmp == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, _tmp);
        }
        if (entity.getNeed() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getNeed());
        }
        statement.bindDouble(4, entity.getLatitude());
        statement.bindDouble(5, entity.getLongitude());
        statement.bindLong(6, entity.getTimestamp());
        if (entity.getVictimId() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getVictimId());
        }
        if (entity.getMessage() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getMessage());
        }
        final int _tmp_1 = entity.isRead() ? 1 : 0;
        statement.bindLong(9, _tmp_1);
        statement.bindLong(10, entity.getId());
      }
    };
    this.__preparedStmtOfMarkAsRead = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE alerts SET isRead = 1 WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteOldAlerts = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM alerts WHERE timestamp < ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertAlert(final AlertEntity alert, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfAlertEntity.insert(alert);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteAlert(final AlertEntity alert, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfAlertEntity.handle(alert);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateAlert(final AlertEntity alert, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfAlertEntity.handle(alert);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object markAsRead(final long alertId, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfMarkAsRead.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, alertId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfMarkAsRead.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteOldAlerts(final long timestamp,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteOldAlerts.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, timestamp);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteOldAlerts.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<AlertEntity>> getAllAlerts() {
    final String _sql = "SELECT * FROM alerts ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"alerts"}, new Callable<List<AlertEntity>>() {
      @Override
      @NonNull
      public List<AlertEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfNeed = CursorUtil.getColumnIndexOrThrow(_cursor, "need");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfVictimId = CursorUtil.getColumnIndexOrThrow(_cursor, "victimId");
          final int _cursorIndexOfMessage = CursorUtil.getColumnIndexOrThrow(_cursor, "message");
          final int _cursorIndexOfIsRead = CursorUtil.getColumnIndexOrThrow(_cursor, "isRead");
          final List<AlertEntity> _result = new ArrayList<AlertEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final AlertEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final AlertMessage.Priority _tmpPriority;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfPriority)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfPriority);
            }
            _tmpPriority = __converters.toPriority(_tmp);
            final String _tmpNeed;
            if (_cursor.isNull(_cursorIndexOfNeed)) {
              _tmpNeed = null;
            } else {
              _tmpNeed = _cursor.getString(_cursorIndexOfNeed);
            }
            final double _tmpLatitude;
            _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            final double _tmpLongitude;
            _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final String _tmpVictimId;
            if (_cursor.isNull(_cursorIndexOfVictimId)) {
              _tmpVictimId = null;
            } else {
              _tmpVictimId = _cursor.getString(_cursorIndexOfVictimId);
            }
            final String _tmpMessage;
            if (_cursor.isNull(_cursorIndexOfMessage)) {
              _tmpMessage = null;
            } else {
              _tmpMessage = _cursor.getString(_cursorIndexOfMessage);
            }
            final boolean _tmpIsRead;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsRead);
            _tmpIsRead = _tmp_1 != 0;
            _item = new AlertEntity(_tmpId,_tmpPriority,_tmpNeed,_tmpLatitude,_tmpLongitude,_tmpTimestamp,_tmpVictimId,_tmpMessage,_tmpIsRead);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<AlertEntity>> getUnreadAlerts() {
    final String _sql = "SELECT * FROM alerts WHERE isRead = 0 ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"alerts"}, new Callable<List<AlertEntity>>() {
      @Override
      @NonNull
      public List<AlertEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfNeed = CursorUtil.getColumnIndexOrThrow(_cursor, "need");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfVictimId = CursorUtil.getColumnIndexOrThrow(_cursor, "victimId");
          final int _cursorIndexOfMessage = CursorUtil.getColumnIndexOrThrow(_cursor, "message");
          final int _cursorIndexOfIsRead = CursorUtil.getColumnIndexOrThrow(_cursor, "isRead");
          final List<AlertEntity> _result = new ArrayList<AlertEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final AlertEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final AlertMessage.Priority _tmpPriority;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfPriority)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfPriority);
            }
            _tmpPriority = __converters.toPriority(_tmp);
            final String _tmpNeed;
            if (_cursor.isNull(_cursorIndexOfNeed)) {
              _tmpNeed = null;
            } else {
              _tmpNeed = _cursor.getString(_cursorIndexOfNeed);
            }
            final double _tmpLatitude;
            _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            final double _tmpLongitude;
            _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final String _tmpVictimId;
            if (_cursor.isNull(_cursorIndexOfVictimId)) {
              _tmpVictimId = null;
            } else {
              _tmpVictimId = _cursor.getString(_cursorIndexOfVictimId);
            }
            final String _tmpMessage;
            if (_cursor.isNull(_cursorIndexOfMessage)) {
              _tmpMessage = null;
            } else {
              _tmpMessage = _cursor.getString(_cursorIndexOfMessage);
            }
            final boolean _tmpIsRead;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsRead);
            _tmpIsRead = _tmp_1 != 0;
            _item = new AlertEntity(_tmpId,_tmpPriority,_tmpNeed,_tmpLatitude,_tmpLongitude,_tmpTimestamp,_tmpVictimId,_tmpMessage,_tmpIsRead);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
