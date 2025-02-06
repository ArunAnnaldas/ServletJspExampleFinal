def highlight_differences_debug(pdf_path, differences, output_path):
    """
    Debug version: Highlights differences in the PDF and logs the search results.

    Args:
        pdf_path (str): Original PDF.
        differences (dict): Differences detected.
        output_path (str): Path to save the highlighted PDF.
    """
    doc = fitz.open(pdf_path)
    for page_num, diffs in differences.items():
        page = doc.load_page(page_num - 1)
        
        print(f"\n🔍 Debugging Page {page_num}:")

        for diff in diffs:
            diff_clean = diff.strip()
            text_instances = page.search_for(diff_clean)

            if text_instances:
                print(f"✅ Found: '{diff_clean}' on Page {page_num}")
                for inst in text_instances:
                    highlight = page.add_highlight_annot(inst)
                    highlight.update()
            else:
                print(f"❌ Not Found: '{diff_clean}' on Page {page_num}")

    doc.save(output_path)
    print(f"✅ Differences highlighted in: {output_path}")
