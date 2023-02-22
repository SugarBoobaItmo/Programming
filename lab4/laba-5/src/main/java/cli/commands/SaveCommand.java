package cli.commands;

import java.util.List;

import collection_manager.AbstractManager;
import csvutils.CsvUtils;
import models.CollectionRecord;

public class SaveCommand extends AbstractCollectionCommand {

    public SaveCommand(String name, String description, AbstractManager manager) {
        super(name, description, manager);
    }

    public void save(CollectionRecord collectionRecord, String filePath) {

    }

    @Override
    public void execute(List<String> inlineParams) {
        if (inlineParams.size() == 1) {

            CsvUtils.recordToCsv(manager.getCollectionRecord(), manager.getInfo().getFilePath());

        } else {
            System.out.println("Incorrect command, please write it without parameters");
        }

    }
}
