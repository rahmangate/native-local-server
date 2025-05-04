import { useEvent } from "expo";
import NativeLocalServer, { NativeLocalServerView } from "native-local-server";
import { useEffect } from "react";
import { Button, SafeAreaView, ScrollView, Text, View } from "react-native";
import RNFS from "react-native-fs";

export default function App() {
  const onChangePayload = useEvent(NativeLocalServer, "onChange");

  useEffect(() => {
    async function start() {
      const dir = RNFS.DocumentDirectoryPath || "";
      await NativeLocalServer.startServer(dir)
        .then((local) => console.log("running at:", local))
        .catch((e) => console.log("error running", e?.toString()));
    }
    start();
  }, []);

  return (
    <SafeAreaView style={styles.container}>
      <ScrollView style={styles.container}>
        <Text style={styles.header}>Module API Example</Text>
        <Group name="Constants">
          <Text>{NativeLocalServer.PI}</Text>
        </Group>
        <Group name="Functions">
          <Text>{NativeLocalServer.hello()}</Text>
        </Group>
        <Group name="Async functions">
          <Button
            title="Set value"
            onPress={async () => {
              await NativeLocalServer.setValueAsync("Hello from JS!");
            }}
          />
        </Group>
        <Group name="Events">
          <Text>{onChangePayload?.value}</Text>
        </Group>
        <Group name="Views">
          <NativeLocalServerView
            url="https://www.example.com"
            onLoad={({ nativeEvent: { url } }) => console.log(`Loaded: ${url}`)}
            style={styles.view}
          />
        </Group>
      </ScrollView>
    </SafeAreaView>
  );
}

function Group(props: { name: string; children: React.ReactNode }) {
  return (
    <View style={styles.group}>
      <Text style={styles.groupHeader}>{props.name}</Text>
      {props.children}
    </View>
  );
}

const styles = {
  header: {
    fontSize: 30,
    margin: 20,
  },
  groupHeader: {
    fontSize: 20,
    marginBottom: 20,
  },
  group: {
    margin: 20,
    backgroundColor: "#fff",
    borderRadius: 10,
    padding: 20,
  },
  container: {
    flex: 1,
    backgroundColor: "#eee",
  },
  view: {
    flex: 1,
    height: 200,
  },
};
