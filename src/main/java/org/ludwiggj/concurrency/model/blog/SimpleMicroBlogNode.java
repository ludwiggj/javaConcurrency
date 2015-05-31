package org.ludwiggj.concurrency.model.blog;

public interface SimpleMicroBlogNode {
  void propagateUpdate(Update upd_, SimpleMicroBlogNode backup_);

  void confirmUpdate(SimpleMicroBlogNode other_, Update update_);

  String getIdent();
}
