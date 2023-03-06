package cli.commands;

import java.util.List;

import cli.commands.checker.Checkers;
import cli.commands.exceptions.ExecuteError;
import cli.interfaces.LineReader;
import cli.interfaces.LineWriter;
import collection_manager.AbstractManager;
import csvutils.CsvUtils;
import models.CollectionRecord;

public class SaveCommand extends AbstractCollectionCommand {

    public SaveCommand(AbstractManager manager) {
        super("Save", "Save collection to file",  manager);
    }

    public void save(CollectionRecord collectionRecord, String filePath) {

    }

    @Override
    public void execute(List<String> inlineParams, LineReader input, LineWriter output) throws ExecuteError {
        Checkers.checkInlineParamsCount(0, inlineParams);
        CsvUtils.recordToCsv(manager.getCollectionRecord(), manager.getInfo().getFilePath());
    }
}
